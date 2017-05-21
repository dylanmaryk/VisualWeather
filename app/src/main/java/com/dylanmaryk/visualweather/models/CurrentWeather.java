package com.dylanmaryk.visualweather.models;

import com.dylanmaryk.visualweather.enums.WeatherType;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CurrentWeather {
  private String summary;
  private float temperature;
  @SerializedName("icon")
  private WeatherType weatherType;

  public String getSummary() {
    return summary;
  }

  public float getTemperature() {
    return temperature;
  }

  public WeatherType getWeatherType() {
    return weatherType;
  }
}
