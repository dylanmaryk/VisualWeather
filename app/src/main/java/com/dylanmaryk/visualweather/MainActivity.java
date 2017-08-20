package com.dylanmaryk.visualweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dylanmaryk.visualweather.forecast.ForecastFragment;
import com.dylanmaryk.visualweather.forecast.ForecastPresenter;
import com.dylanmaryk.visualweather.lifecycle.LifecycleHandler;
import com.dylanmaryk.visualweather.storage.DataStorage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    LifecycleHandler lifecycleHandler = new LifecycleHandler(getApplication());

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    ForecastFragment forecastFragment = new ForecastFragment();
    DataStorage dataStorage = new DataStorage(this);
    ForecastPresenter forecastPresenter =
        new ForecastPresenter(forecastFragment, lifecycleHandler, dataStorage);
    forecastFragment.setPresenter(forecastPresenter);
    getSupportFragmentManager().beginTransaction().add(R.id.root_layout, forecastFragment).commit();

    ImageLoaderConfiguration imageLoaderConfiguration =
        new ImageLoaderConfiguration.Builder(this).build();
    ImageLoader.getInstance().init(imageLoaderConfiguration);
  }
}
