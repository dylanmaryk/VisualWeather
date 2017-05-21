package com.dylanmaryk.visualweather.forecast;

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
            String summary = forecast.getCurrentWeather().getSummary();
            view.setSummary(summary);

            float temperatureFloat = forecast.getCurrentWeather().getTemperature();
            int temperatureRounded = Math.round(temperatureFloat);
            String temperature = Integer.toString(temperatureRounded);
            view.setTemperature(temperature);
          }

          @Override
          public void onError(@NonNull Throwable e) {
            System.out.println(e);
          }

          @Override
          public void onComplete() {
            System.out.println("Complete");
          }
        });
  }
}
