package com.dylanmaryk.visualweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dylanmaryk.visualweather.forecast.ForecastFragment;
import com.dylanmaryk.visualweather.forecast.ForecastPresenter;
import com.dylanmaryk.visualweather.lifecycle.LifecycleHandler;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    LifecycleHandler lifecycleHandler = new LifecycleHandler(getApplication());

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    ForecastFragment forecastFragment = new ForecastFragment();
    ForecastPresenter forecastPresenter = new ForecastPresenter(forecastFragment, lifecycleHandler);
    forecastFragment.setPresenter(forecastPresenter);
    getSupportFragmentManager().beginTransaction().add(R.id.root_layout, forecastFragment).commit();
  }
}
