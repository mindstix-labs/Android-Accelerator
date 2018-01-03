/*
 * Copyright (c) 2017-18 Mindstix Software Labs, Inc.
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

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindstix.baseline.FlavorSpecific;
import com.mindstix.baseline.R;
import com.mindstix.baseline.network.model.forecast.ForecastList;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

/**
 * RecyclerView Adapter to show 5 days forecast for a city in RecyclerView.
 *
 * @author Mindstix Software Labs, Inc.
 */

public class HomeAdapter extends RecyclerView.Adapter <HomeAdapter.HomeViewHolder> {

    Activity mainActivity = null;
    List<ForecastList> forecastList = null;

    public HomeAdapter(Activity mainActivity, List<ForecastList> forecastList) {
        this.mainActivity = mainActivity;
        this.forecastList = forecastList;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.weather_details, parent, false);

        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.bindData(forecastList.get(position));
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    /**
     * ViewHolder class for state management. Helps cache list items and retrieve faster during quick scroll.
     */
    class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView forecastTimestamp;
        ImageView weatherImage;
        TextView cloudStatus;
        TextView temperature;
        TextView minimumTemperature;
        TextView maximumTemperature;
        TextView pressure;
        TextView humidity;
        TextView wind;

        public HomeViewHolder(View itemView) {
            super(itemView);

            forecastTimestamp = itemView.findViewById(R.id.forecast_timestamp);
            weatherImage = itemView.findViewById(R.id.forecast_image);
            cloudStatus = itemView.findViewById(R.id.forecast_weather_condition);
            temperature = itemView.findViewById(R.id.forecast_temperature);
            minimumTemperature = itemView.findViewById(R.id.forecast_minimum);
            maximumTemperature = itemView.findViewById(R.id.forecast_maximum);
            pressure = itemView.findViewById(R.id.forecast_pressure);
            humidity = itemView.findViewById(R.id.forecast_humidity);
            wind = itemView.findViewById(R.id.forecast_wind);
        }

        public void bindData(ForecastList forecastData) {

            // Show weather data captured time.
            if (forecastData.getForecastMillis() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(forecastData.getForecastMillis());
                forecastTimestamp.setText("Forecast: " + DateFormat.format("dd:MM:yyyy hh:mm", calendar));
            }

            if (forecastData.getWeatherDataList() != null && forecastData.getWeatherDataList().size() != 0) {

                // Show weather condition icon.
                if (!TextUtils.isEmpty(forecastData.getWeatherDataList().get(0).getIcon())) {
                    Picasso.with(mainActivity).load(FlavorSpecific.ICON_BASE_URL +
                            forecastData.getWeatherDataList().get(0).getIcon() + ".png").into(weatherImage);
                }

                // Show weather condition status.
                if (!TextUtils.isEmpty(forecastData.getWeatherDataList().get(0).getCondition())) {
                    cloudStatus.setText("Sky: " +  forecastData.getWeatherDataList().get(0).getCondition());
                }
            }

            if (forecastData.getForecastData() != null) {

                // Show temperature.
                if (forecastData.getForecastData().getTemperature() != null) {
                    temperature.setText("Temperature: " + forecastData.getForecastData().getTemperature());
                }

                // Show minimum temperature.
                if (forecastData.getForecastData().getMinimumTemperature() != null) {
                    minimumTemperature.setText("Minimum: " + forecastData.getForecastData().getMinimumTemperature());
                }

                // Show maximum temperature.
                if (forecastData.getForecastData().getMaximumTemperature() != null) {
                    maximumTemperature.setText("Maximum: " + forecastData.getForecastData().getMaximumTemperature());
                }

                // Show pressure.
                if (forecastData.getForecastData().getPressure() != null) {
                    pressure.setText("Pressure: " + forecastData.getForecastData().getPressure());
                }

                // Show humidity.
                if (forecastData.getForecastData().getHumidity() != null) {
                    humidity.setText("Humidity: " + forecastData.getForecastData().getHumidity());
                }
            }

            if (forecastData.getWind() != null) {

                // Show wind speed.
                if (forecastData.getWind().getSpeed() != null) {
                    wind.setText("Wind: " + forecastData.getWind().getSpeed());
                }
            }
        }
    }
}
