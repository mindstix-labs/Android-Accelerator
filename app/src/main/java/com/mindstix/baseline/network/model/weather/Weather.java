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

package com.mindstix.baseline.network.model.weather;

import com.google.gson.annotations.SerializedName;
import com.mindstix.baseline.network.model.common.Clouds;
import com.mindstix.baseline.network.model.common.ForecastData;
import com.mindstix.baseline.network.model.common.GeoLocation;
import com.mindstix.baseline.network.model.common.WeatherData;
import com.mindstix.baseline.network.model.common.Wind;

import java.util.List;

/**
 * POJO class - Top level class of Weather API response.
 */
public class Weather {

    @SerializedName("coord")
    private GeoLocation geoLocation;

    @SerializedName("weather")
    private List<WeatherData> weatherDataList = null;

    private String base;

    @SerializedName("main")
    private ForecastData forecastData;

    private Integer visibility;

    private Wind wind;

    private Clouds clouds;

    @SerializedName("dt")
    private Integer timeMillis;

    private Sys sys;

    @SerializedName("id")
    private Integer cityId;

    @SerializedName("name")
    private String cityName;

    private Integer cod;

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public String getBase() {
        return base;
    }

    public ForecastData getForecastData() {
        return forecastData;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Integer getTimeMillis() {
        return timeMillis;
    }

    public Sys getSys() {
        return sys;
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public Integer getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
