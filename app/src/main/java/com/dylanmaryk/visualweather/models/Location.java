package com.dylanmaryk.visualweather.models;

import com.google.android.gms.maps.model.LatLng;

public class Location {
  private LatLng latLng;

  public Location(LatLng latLng) {
    this.latLng = latLng;
  }

  public LatLng getLatLng() {
    return latLng;
  }
}
