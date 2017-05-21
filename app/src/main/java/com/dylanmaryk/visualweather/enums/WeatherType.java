package com.dylanmaryk.visualweather.enums;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public enum WeatherType {
  @SerializedName("clear-day")
  CLEAR_DAY,
  @SerializedName("clear-night")
  CLEAR_NIGHT,
  @SerializedName("rain")
  RAIN,
  @SerializedName("snow")
  SNOW,
  @SerializedName("sleet")
  SLEET,
  @SerializedName("wind")
  WIND,
  @SerializedName("fog")
  FOG,
  @SerializedName("cloudy")
  CLOUDY,
  @SerializedName("partly-cloudy-day")
  PARTLY_CLOUDY_DAY,
  @SerializedName("partly-cloudy-night")
  PARTLY_CLOUDY_NIGHT
}
