package com.dylanmaryk.visualweather.networking;

import com.dylanmaryk.visualweather.models.LocationPhotosWrapper;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface FiveHundredPxService {
  @GET("v1/photos/search?sort=highest_rating&image_size=2048&rpp=3")
  Observable<LocationPhotosWrapper> getLocationPhotosWrapper(@Query("consumer_key") String key,
                                                             @Query("geo") String latLng,
                                                             @Query("term") String name);
}
