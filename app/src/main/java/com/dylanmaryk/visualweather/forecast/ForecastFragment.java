package com.dylanmaryk.visualweather.forecast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cesards.cropimageview.CropImageView;
import com.dylanmaryk.visualweather.R;
import com.dylanmaryk.visualweather.models.Location;
import com.dylanmaryk.visualweather.storage.DataStorage;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

public class ForecastFragment extends Fragment implements ForecastContract.View {
  @BindView(R.id.viewFlipper)
  ViewFlipper viewFlipper;
  @BindView(R.id.weatherView)
  WeatherView weatherView;
  @BindView(R.id.summaryText)
  TextView summaryText;
  @BindView(R.id.temperatureText)
  TextView temperateText;

  private ForecastContract.Presenter forecastPresenter;
  private static long FADE_ANIMATION_DURATION = 3000;
  private static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_forecast, container, false);
    ButterKnife.bind(this, view);
    setupViewFlipperAnimations();
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    forecastPresenter.start();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    forecastPresenter.stop();
  }

  private void setupViewFlipperAnimations() {
    Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
    Animation fadeOutAnimation =
        AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
    fadeInAnimation.setDuration(FADE_ANIMATION_DURATION);
    fadeOutAnimation.setDuration(FADE_ANIMATION_DURATION);
    viewFlipper.setInAnimation(fadeInAnimation);
    viewFlipper.setOutAnimation(fadeOutAnimation);
  }

  @Override
  public void setPresenter(ForecastContract.Presenter presenter) {
    forecastPresenter = presenter;
  }

  @Override
  public void setSummary(String summary) {
    summaryText.setText(summary);
  }

  @Override
  public void setTemperature(String temperature) {
    temperateText.setText(temperature);
  }

  @Override
  public void showClearWeather() {
    weatherView.setWeather(Constants.weatherStatus.SUN);
    weatherView.startAnimation();
  }

  @Override
  public void showRain() {
    weatherView.setWeather(Constants.weatherStatus.RAIN);
    weatherView.startAnimation();
  }

  @Override
  public void showSnow() {
    weatherView.setWeather(Constants.weatherStatus.SNOW);
    weatherView.startAnimation();
  }

  @Override
  public void addPhoto(Bitmap photo) {
    CropImageView imageView = new CropImageView(getActivity());
    imageView.setImageBitmap(photo);
    imageView.setCropType(CropImageView.CropType.LEFT_CENTER);
    viewFlipper.addView(imageView);
  }

  @Override
  public void clearPhotos() {
    viewFlipper.removeAllViews();
  }

  @OnClick(R.id.searchButton)
  public void searchButtonClick() {
    forecastPresenter.setupPlaceAutocomplete();
  }

  @Override
  public void showPlaceAutocomplete(PlaceAutocomplete.IntentBuilder intentBuilder) {
    try {
      Intent intent = intentBuilder.build(getActivity());
      startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Place place = PlaceAutocomplete.getPlace(getActivity(), data);
    forecastPresenter.requestForecast(place);
  }
}
