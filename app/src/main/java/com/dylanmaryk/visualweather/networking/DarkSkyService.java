package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.models.Forecast;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface DarkSkyService {
  @GET("forecast/{key}/{latLng}?exclude=[minutely,hourly,daily,alerts,flags]&units=si")
  Observable<Forecast> getForecast(@Path("key") String key, @Path("latLng") String latLng);
}
