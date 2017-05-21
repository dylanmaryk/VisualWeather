package com.dylanmaryk.visualweather.forecast;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dylanmaryk.visualweather.R;

public class ForecastFragment extends Fragment implements ForecastContract.View {
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
}
