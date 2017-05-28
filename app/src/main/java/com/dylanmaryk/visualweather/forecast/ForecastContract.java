package com.dylanmaryk.visualweather.forecast;

import android.graphics.Bitmap;
import com.dylanmaryk.visualweather.BasePresenter;
import com.dylanmaryk.visualweather.BaseView;

interface ForecastContract {
  interface View extends BaseView<Presenter> {
    void setSummary(String summary);

    void setTemperature(String temperature);

    void showClearWeather();

    void showRain();

    void showSnow();

    void addPhoto(Bitmap photo);
  }

  interface Presenter extends BasePresenter {

  }
}
