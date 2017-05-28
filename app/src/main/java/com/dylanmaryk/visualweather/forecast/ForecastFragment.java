package com.dylanmaryk.visualweather.forecast;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dylanmaryk.visualweather.R;
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

  private ForecastContract.Presenter presenter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_forecast, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.start();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    presenter.stop();
  }

  @Override
  public void setPresenter(ForecastContract.Presenter presenter) {
    this.presenter = presenter;
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
    ImageView imageView = new ImageView(getActivity());
    imageView.setImageBitmap(photo);
    viewFlipper.addView(imageView);
  }
}
