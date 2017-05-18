package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.models.Forecast;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DarkSkyService {
  @GET("forecast/{key}/37.8267,-122.4233")
  Observable<Forecast> getForecast(@Path("key") String key);
}
