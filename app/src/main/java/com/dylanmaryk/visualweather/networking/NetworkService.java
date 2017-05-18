package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.BuildConfig;
import com.dylanmaryk.visualweather.models.Forecast;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
  private static final NetworkService networkService = new NetworkService();

  public static NetworkService getNetworkService() {
    return networkService;
  }

  private DarkSkyService darkSkyService;

  private NetworkService() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.darksky.net")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    darkSkyService = retrofit.create(DarkSkyService.class);
  }

  public Observable<Forecast> getForecast() {
    return darkSkyService.getForecast(BuildConfig.DARK_SKY_API_KEY);
  }
}
