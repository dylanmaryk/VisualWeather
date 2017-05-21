package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.models.Forecast;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DarkSkyService {
  @GET("forecast/{key}/52.52,13.405?exclude=[minutely,hourly,daily,alerts,flags]&units=si")
  Observable<Forecast> getForecast(@Path("key") String key);
}
