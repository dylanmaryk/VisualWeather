package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.BuildConfig;
import com.dylanmaryk.visualweather.models.Forecast;
import com.dylanmaryk.visualweather.models.LocationPhoto;
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

  public Observable<Forecast> getForecast() {
    return darkSkyService.getForecast(BuildConfig.DARK_SKY_API_KEY);
  }

  public Observable<ArrayList<LocationPhoto>> getLocationPhotos() {
    return fiveHundredPxService
        .getLocationPhotosWrapper(BuildConfig.FIVE_HUNDRED_PX_API_KEY)
        .map(locationPhotosWrapper -> locationPhotosWrapper.getLocationPhotos());
  }
}
