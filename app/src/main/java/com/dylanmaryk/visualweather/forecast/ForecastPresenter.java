package com.dylanmaryk.visualweather.forecast;

import android.graphics.Bitmap;
import android.view.View;
import com.dylanmaryk.visualweather.enums.WeatherType;
import com.dylanmaryk.visualweather.lifecycle.LifecycleHandler;
import com.dylanmaryk.visualweather.models.CurrentWeather;
import com.dylanmaryk.visualweather.models.Forecast;
import com.dylanmaryk.visualweather.models.Location;
import com.dylanmaryk.visualweather.models.LocationPhoto;
import com.dylanmaryk.visualweather.networking.NetworkService;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForecastPresenter implements ForecastContract.Presenter {
  private final ForecastContract.View forecastView;
  private final LifecycleHandler lifecycleHandler;

  public ForecastPresenter(ForecastContract.View view, LifecycleHandler lifecycleHandler) {
    this.forecastView = view;
    this.lifecycleHandler = lifecycleHandler;
  }

  @Override
  public void start() {
  }

  @Override
  public void stop() {
  }

  @Override
  public void requestForecast(Place place) {
    Location location = new Location(place.getLatLng());
    requestForecast(location);
    requestPhotos(location);
  }

  private void requestForecast(Location location) {
    NetworkService
        .getNetworkService()
        .getForecast(location)
        .compose(lifecycleHandler.<Forecast>bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Forecast>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {
          }

          @Override
          public void onNext(@NonNull Forecast forecast) {
            CurrentWeather currentWeather = forecast.getCurrentWeather();
            setSummary(currentWeather.getSummary());
            setTemperature(currentWeather.getTemperature());
            setWeatherType(currentWeather.getWeatherType());
          }

          @Override
          public void onError(@NonNull Throwable e) {
            e.printStackTrace();
          }

          @Override
          public void onComplete() {
          }
        });
  }

  private void requestPhotos(Location location) {
    NetworkService
        .getNetworkService()
        .getPhotos(location)
        .flatMapIterable(locationPhoto -> locationPhoto)
        .compose(lifecycleHandler.<LocationPhoto>bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<LocationPhoto>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {
            clearPhotos();
          }

          @Override
          public void onNext(@NonNull LocationPhoto locationPhoto) {
            addPhotoFromURL(locationPhoto.getImageURL());
          }

          @Override
          public void onError(@NonNull Throwable e) {
            e.printStackTrace();
          }

          @Override
          public void onComplete() {
          }
        });
  }

  private void setSummary(String summary) {
    forecastView.setSummary(summary);
  }

  private void setTemperature(float temperature) {
    int temperatureRounded = Math.round(temperature);
    String temperatureString = Integer.toString(temperatureRounded);
    forecastView.setTemperature(temperatureString);
  }

  private void setWeatherType(WeatherType weatherType) {
    switch (weatherType) {
      case CLEAR_DAY:
        forecastView.showClearWeather();
        break;
      case CLEAR_NIGHT:
        forecastView.showClearWeather();
        break;
      case RAIN:
        forecastView.showRain();
        break;
      case SNOW:
        forecastView.showSnow();
        break;
      case SLEET:
        forecastView.showSnow();
        break;
      case WIND:
        forecastView.showClearWeather();
        break;
      case FOG:
        forecastView.showClearWeather();
        break;
      case CLOUDY:
        forecastView.showClearWeather();
        break;
      case PARTLY_CLOUDY_DAY:
        forecastView.showClearWeather();
        break;
      case PARTLY_CLOUDY_NIGHT:
        forecastView.showClearWeather();
        break;
    }
  }

  private void addPhotoFromURL(String photoURL) {
    ImageLoader.getInstance().loadImage(photoURL, new ImageLoadingListener() {
      @Override
      public void onLoadingStarted(String imageUri, View view) {
      }

      @Override
      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        System.out.println(failReason);
      }

      @Override
      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        forecastView.addPhoto(loadedImage);
      }

      @Override
      public void onLoadingCancelled(String imageUri, View view) {
      }
    });
  }

  private void clearPhotos() {
    forecastView.clearPhotos();
  }

  @Override
  public void setupPlaceAutocomplete() {
    AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
        .build();
    PlaceAutocomplete.IntentBuilder intentBuilder =
        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(
            autocompleteFilter);
    forecastView.showPlaceAutocomplete(intentBuilder);
  }
}
