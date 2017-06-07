package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.BuildConfig;
import com.dylanmaryk.visualweather.enums.WeatherType;
import com.dylanmaryk.visualweather.models.Forecast;
import com.dylanmaryk.visualweather.models.Location;
import com.dylanmaryk.visualweather.models.LocationPhoto;
import com.dylanmaryk.visualweather.models.LocationPhotosWrapper;
import com.google.android.gms.maps.model.LatLng;
import io.reactivex.Observable;
import java.util.ArrayList;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
  private static final NetworkService networkService = new NetworkService();

  public static NetworkService getNetworkService() {
    return networkService;
  }

  private DarkSkyService darkSkyService;
  private FiveHundredPxService fiveHundredPxService;

  private NetworkService() {
    createDarkSkyService();
    createFiveHundredPxService();
  }

  private void createDarkSkyService() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.darksky.net")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    darkSkyService = retrofit.create(DarkSkyService.class);
  }

  private void createFiveHundredPxService() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.500px.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    fiveHundredPxService = retrofit.create(FiveHundredPxService.class);
  }

  public Observable<Forecast> getForecast(Location location) {
    LatLng latLng = location.getLatLng();
    String latLngString = latLng.latitude + "," + latLng.longitude;
    return darkSkyService.getForecast(BuildConfig.DARK_SKY_API_KEY, latLngString);
  }

  public Observable<ArrayList<LocationPhoto>> getPhotos(Location location,
                                                        WeatherType weatherType) {
    LatLng latLng = location.getLatLng();
    String latLngRadiusString = latLng.latitude + "," + latLng.longitude + ",5km";
    String searchTerm = location.getName() + " " + weatherSearchTerm(weatherType);
    return fiveHundredPxService
        .getLocationPhotosWrapper(BuildConfig.FIVE_HUNDRED_PX_API_KEY,
                                  latLngRadiusString,
                                  searchTerm)
        .map(LocationPhotosWrapper::getLocationPhotos);
  }

  private String weatherSearchTerm(WeatherType weatherType) {
    switch (weatherType) {
      case CLEAR_DAY:
        return "clear day";
      case CLEAR_NIGHT:
        return "clear night";
      case RAIN:
        return "rain";
      case SNOW:
        return "snow";
      case SLEET:
        return "snow";
      case WIND:
        return "wind";
      case FOG:
        return "fog";
      case CLOUDY:
        return "cloudy";
      case PARTLY_CLOUDY_DAY:
        return "cloudy day";
      case PARTLY_CLOUDY_NIGHT:
        return "cloudy night";
      default:
        return "";
    }
  }
}
