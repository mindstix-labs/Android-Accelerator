/*
 * Copyright (c) 2017 Mindstix Software Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mindstix.baseline.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.mindstix.baseline.FlavorSpecific;
import com.mindstix.baseline.R;
import com.mindstix.baseline.analytics.Analytics;
import com.mindstix.baseline.network.api.ApiClient;
import com.mindstix.baseline.network.api.ApiInterface;
import com.mindstix.baseline.network.model.forecast.Forecast;
import com.mindstix.baseline.network.model.weather.Weather;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * HomeFragment to show weather forecast of city.
 * - Current weather.
 * - Forecast for next 5 days.
 *
 * @author Mindstix Software Labs, Inc.
 */

public class HomeFragment extends Fragment {

    // Constants declaration.
    private static final String CITY_NAME = "Pune";
    private static final String COUNTRY_ID = "IN";

    // Singleton analytics instance.
    Analytics analyticsInstance = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Update tool bar title.
        getActivity().setTitle(R.string.home_fragment_title);

        // Inflate Home Fragment XML layout.
        View homeFragmentView = inflater.inflate(R.layout.fragment_home, null);

        getWeatherData(homeFragmentView);
        getForecastData(homeFragmentView);

        // Initialise analytics instance.
        try {
            analyticsInstance = Analytics.getInstance(Analytics.PLATFORM_FIREBASE_ANALYTICS, getActivity());
        } catch (Exception exception) {
            FirebaseCrash.report(exception);
            exception.printStackTrace();
        }

        // Track analytics events at the end of the block.
        if (analyticsInstance != null) {
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put(Analytics.PARAM_ID, "1");
            eventParams.put(Analytics.PARAM_NAME, "HomeFragment");
            eventParams.put(Analytics.PARAM_TYPE, "onCreateView");
            eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

            try {
                analyticsInstance.logEvent(eventParams);
            } catch (Exception e) {
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
        }

        return homeFragmentView;
    }

    /**
     * API call to get current weather data.
     * http://api.openweathermap.org/data/2.5/weather?q=London,UK&appid=af921fb4e45cceda08b7ce7f63c71005
     *
     * @param homeFragmentView - Instance of home fragment container.
     */
    private void getWeatherData(final View homeFragmentView) {

        // Get instance of API interface.
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Weather> weatherDataApiCall = apiService.getWeatherData(CITY_NAME + "," + COUNTRY_ID, FlavorSpecific.APP_ID);
        Log.d(FlavorSpecific.APP_LOG_TAG, weatherDataApiCall.request().url().toString());

        weatherDataApiCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                showWeatherData(response.body(), homeFragmentView);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(FlavorSpecific.APP_LOG_TAG, t.getMessage());
            }
        });
    }

    /**
     * Bind current weather data received from API to UI.
     *
     * @param weatherData - Weather data received from API.
     * @param homeFragmentView - Instance of home fragment container.
     */
    private void showWeatherData(Weather weatherData, View homeFragmentView) {

        if (weatherData == null) {
            Toast.makeText(getActivity(), "Failed to get weather data.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show city name.
        if (!TextUtils.isEmpty(weatherData.getCityName())) {
            TextView cityName = homeFragmentView.findViewById(R.id.forecast_city);
            cityName.setText(weatherData.getCityName());
            Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getCityName());
        }

        if (weatherData.getGeoLocation() != null) {

            // Show city latitude.
            if (weatherData.getGeoLocation().getLatitude() != null) {
                TextView latitude = homeFragmentView.findViewById(R.id.city_latitude);
                latitude.setText("Latitude    : " + weatherData.getGeoLocation().getLatitude().toString());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getGeoLocation().getLatitude().toString());
            }

            // Show city longitude.
            if (weatherData.getGeoLocation().getLongitude() != null) {
                TextView longitude = homeFragmentView.findViewById(R.id.city_longitude);
                longitude.setText("Longitude : " + weatherData.getGeoLocation().getLongitude().toString());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getGeoLocation().getLongitude().toString());
            }
        }

        // Show weather data captured time.
        if (weatherData.getTimeMillis() != null) {
            TextView forecastTimestamp = homeFragmentView.findViewById(R.id.forecast_timestamp);
            forecastTimestamp.setText("Today's Forecast");
        }

        if (weatherData.getWeatherDataList() != null && weatherData.getWeatherDataList().size() != 0) {

            // Show weather condition icon.
            if (!TextUtils.isEmpty(weatherData.getWeatherDataList().get(0).getIcon())) {
                ImageView weatherImage = homeFragmentView.findViewById(R.id.forecast_image);
                Picasso.with(getActivity()).load(FlavorSpecific.ICON_BASE_URL +
                        weatherData.getWeatherDataList().get(0).getIcon() + ".png").into(weatherImage);
                Log.d(FlavorSpecific.APP_LOG_TAG, FlavorSpecific.ICON_BASE_URL +
                        weatherData.getWeatherDataList().get(0).getIcon() + ".png");
            }

            // Show weather condition status.
            if (!TextUtils.isEmpty(weatherData.getWeatherDataList().get(0).getCondition())) {
                TextView cloudStatus = homeFragmentView.findViewById(R.id.forecast_weather_condition);
                cloudStatus.setText("Sky: " +  weatherData.getWeatherDataList().get(0).getCondition());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getWeatherDataList().get(0).getCondition());
            }
        }

        if (weatherData.getForecastData() != null) {

            // Show temperature.
            if (weatherData.getForecastData().getTemperature() != null) {
                TextView temperature = homeFragmentView.findViewById(R.id.forecast_temperature);
                temperature.setText("Temperature: " + weatherData.getForecastData().getTemperature());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getForecastData().getTemperature() + "");
            }

            // Show minimum temperature.
            if (weatherData.getForecastData().getMinimumTemperature() != null) {
                TextView minimumTemperature = homeFragmentView.findViewById(R.id.forecast_minimum);
                minimumTemperature.setText("Minimum: " + weatherData.getForecastData().getMinimumTemperature());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getForecastData().getMinimumTemperature() + "");
            }

            // Show maximum temperature.
            if (weatherData.getForecastData().getMaximumTemperature() != null) {
                TextView maximumTemperature = homeFragmentView.findViewById(R.id.forecast_maximum);
                maximumTemperature.setText("Maximum: " + weatherData.getForecastData().getMaximumTemperature());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getForecastData().getMaximumTemperature() + "");
            }

            // Show pressure.
            if (weatherData.getForecastData().getPressure() != null) {
                TextView pressure = homeFragmentView.findViewById(R.id.forecast_pressure);
                pressure.setText("Pressure: " + weatherData.getForecastData().getPressure());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getForecastData().getPressure() + "");
            }

            // Show humidity.
            if (weatherData.getForecastData().getHumidity() != null) {
                TextView humidity = homeFragmentView.findViewById(R.id.forecast_humidity);
                humidity.setText("Humidity: " + weatherData.getForecastData().getHumidity());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getForecastData().getHumidity() + "");
            }
        }

        if (weatherData.getWind() != null) {

            // Show wind speed.
            if (weatherData.getWind().getSpeed() != null) {
                TextView wind = homeFragmentView.findViewById(R.id.forecast_wind);
                wind.setText("Wind: " + weatherData.getWind().getSpeed());
                Log.d(FlavorSpecific.APP_LOG_TAG, weatherData.getWind().getSpeed() + "");
            }
        }

        if (weatherData.getSys() != null) {

            // Show sunrise time.
            if (weatherData.getSys().getSunriseTimeMillis() != null) {
                TextView sunriseTimestamp = homeFragmentView.findViewById(R.id.forecast_sunrise);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(weatherData.getSys().getSunriseTimeMillis());
                sunriseTimestamp.setText("Sunrise:" + DateFormat.format("hh:mm", calendar));
            }

            // Show sunset time.
            if (weatherData.getSys().getSunsetTimeMillis() != null) {
                TextView sunsetTimestamp = homeFragmentView.findViewById(R.id.forecast_sunset);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(weatherData.getSys().getSunsetTimeMillis());
                sunsetTimestamp.setText("Sunset:" + DateFormat.format("hh:mm", calendar));
            }
        }
    }

    /**
     * API call to get forecast weather data.
     * http://api.openweathermap.org/data/2.5/forecast?q=London,UK&appid=af921fb4e45cceda08b7ce7f63c71005
     *
     * @param homeFragmentView - Instance of home fragment container.
     */
    private void getForecastData(final View homeFragmentView) {

        // Get instance of API interface.
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Forecast> forecastDataApiCall = apiService.getForecastData(CITY_NAME + "," + COUNTRY_ID, FlavorSpecific.APP_ID);

        forecastDataApiCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                showForecastData(response.body(), homeFragmentView);
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Toast.makeText(getActivity(), "Forecast API failed to provide forecast data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Bind forecast weather data received from API to UI.
     *
     * @param forecastData - Forecast data received from API.
     * @param homeFragmentView - Instance of home fragment container.
     */
    private void showForecastData(Forecast forecastData, View homeFragmentView) {

        if (forecastData == null || forecastData.getForecastList() == null || forecastData.getForecastList().size() == 0) {
            Toast.makeText(getActivity(), "Failed to get forecast data.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show forecast data in Recycler View.
        RecyclerView recyclerView = homeFragmentView.findViewById(R.id.forecast_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new HomeAdapter(getActivity(), forecastData.getForecastList()));
        Log.d(FlavorSpecific.APP_LOG_TAG, "Forecast List Size = " + forecastData.getForecastList().size());
    }
}
