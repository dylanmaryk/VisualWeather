package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.models.LocationPhotosWrapper;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FiveHundredPxService {
  @GET("v1/photos/search?geo=52.5200,13.4050,"
      + "5km&term=berlin%20rain&sort=highest_rating&image_size=2048&rpp=3")
  Observable<LocationPhotosWrapper> getLocationPhotosWrapper(@Query("consumer_key") String key);
}
