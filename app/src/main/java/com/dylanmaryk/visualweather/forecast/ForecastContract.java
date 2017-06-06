package com.dylanmaryk.visualweather.forecast;

import android.graphics.Bitmap;
import com.dylanmaryk.visualweather.BasePresenter;
import com.dylanmaryk.visualweather.BaseView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

interface ForecastContract {
  interface View extends BaseView<Presenter> {
    void setSummary(String summary);

    void setTemperature(String temperature);

    void showClearWeather();

    void showRain();

    void showSnow();

    void addPhoto(Bitmap photo);

    void showPlaceAutocomplete(PlaceAutocomplete.IntentBuilder intentBuilder);
  }

  interface Presenter extends BasePresenter {
    void requestForecast(Place place);

    void setupPlaceAutocomplete();
  }
}
