package com.dylanmaryk.visualweather.models;

import com.google.android.gms.maps.model.LatLng;

public class Location {
  private String name;
  private LatLng latLng;

  public Location(CharSequence name, LatLng latLng) {
    this.name = name.toString();
    this.latLng = latLng;
  }

  public String getName() {
    return name;
  }

  public LatLng getLatLng() {
    return latLng;
  }
}
