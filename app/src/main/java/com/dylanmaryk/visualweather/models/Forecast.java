package com.dylanmaryk.visualweather.models;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Forecast {
  @SerializedName("currently")
  private CurrentWeather currentWeather;

  public CurrentWeather getCurrentWeather() {
    return currentWeather;
  }
}

