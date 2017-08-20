package com.dylanmaryk.visualweather.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.dylanmaryk.visualweather.models.Location;
import com.google.gson.Gson;

public class DataStorage {
  private final SharedPreferences sharedPreferences;
  private static String LOCATION_KEY = "location";

  public DataStorage(Activity activity) {
    this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
  }

  public void saveLocation(Location location) {
    String locationJson = new Gson().toJson(location);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(LOCATION_KEY, locationJson);
    editor.apply();
  }

  public Location getLocation() {
    String locationJson = sharedPreferences.getString(LOCATION_KEY, null);
    return new Gson().fromJson(locationJson, Location.class);
  }
}
