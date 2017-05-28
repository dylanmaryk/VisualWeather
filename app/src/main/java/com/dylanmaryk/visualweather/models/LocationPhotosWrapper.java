package com.dylanmaryk.visualweather.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class LocationPhotosWrapper {
  @SerializedName("photos")
  private ArrayList<LocationPhoto> locationPhotos;

  public ArrayList<LocationPhoto> getLocationPhotos() {
    return locationPhotos;
  }
}
