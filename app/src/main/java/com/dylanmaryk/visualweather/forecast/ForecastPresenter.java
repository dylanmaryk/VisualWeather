package com.dylanmaryk.visualweather.forecast;

import com.dylanmaryk.visualweather.enums.WeatherType;
import com.dylanmaryk.visualweather.lifecycle.LifecycleHandler;
import com.dylanmaryk.visualweather.models.Forecast;
import com.dylanmaryk.visualweather.networking.NetworkService;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ForecastPresenter implements ForecastContract.Presenter {
  private final ForecastContract.View view;
  private final LifecycleHandler lifecycleHandler;

  public ForecastPresenter(ForecastContract.View view, LifecycleHandler lifecycleHandler) {
    this.view = view;
    this.lifecycleHandler = lifecycleHandler;
  }

  @Override
  public void start() {
    requestForecast();
  }

  @Override
  public void stop() {
  }

  private void requestForecast() {
    NetworkService.getNetworkService()
        .getForecast()
        .compose(lifecycleHandler.<Forecast>bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<Forecast>() {
          @Override
          public void onNext(@NonNull Forecast forecast) {
            setSummary(forecast.getCurrentWeather().getSummary());
            setTemperature(forecast.getCurrentWeather().getTemperature());
            setWeatherType(forecast.getCurrentWeather().getWeatherType());
          }

          @Override
          public void onError(@NonNull Throwable e) {
            System.out.println(e);
          }

          @Override
          public void onComplete() {
          }
        });
  }

  private void setSummary(String summary) {
    view.setSummary(summary);
  }

  private void setTemperature(float temperature) {
    int temperatureRounded = Math.round(temperature);
    String temperatureString = Integer.toString(temperatureRounded);
    view.setTemperature(temperatureString);
  }

  private void setWeatherType(WeatherType weatherType) {
    if (weatherType == null) {
      return;
    }

    switch (weatherType) {
      case CLEAR_DAY:
        view.showClearWeather();
        break;
      case CLEAR_NIGHT:
        view.showClearWeather();
        break;
      case RAIN:
        view.showRain();
        break;
      case SNOW:
        view.showSnow();
        break;
      case SLEET:
        view.showSnow();
        break;
      case WIND:
        view.showClearWeather();
        break;
      case FOG:
        view.showClearWeather();
        break;
      case CLOUDY:
        view.showClearWeather();
        break;
      case PARTLY_CLOUDY_DAY:
        view.showClearWeather();
        break;
      case PARTLY_CLOUDY_NIGHT:
        view.showClearWeather();
        break;
    }
  }
}
