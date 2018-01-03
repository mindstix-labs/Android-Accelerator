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

package com.mindstix.baseline.network.model.forecast;

import com.google.gson.annotations.SerializedName;
import com.mindstix.baseline.network.model.common.Clouds;
import com.mindstix.baseline.network.model.common.ForecastData;
import com.mindstix.baseline.network.model.common.WeatherData;
import com.mindstix.baseline.network.model.common.Wind;

import java.util.List;

/**
 * POJO class - List of forecast details for different data & time.
 */
public class ForecastList {

    @SerializedName("dt")
    private Integer forecastMillis;

    @SerializedName("main")
    private ForecastData forecastData;

    @SerializedName("weather")
    private List<WeatherData> weatherDataList = null;

    private Clouds clouds;

    private Wind wind;

    private Snow snow;

    private Sys sys;

    @SerializedName("dt_txt")
    private String forecastDate;

    public Integer getForecastMillis() {
        return forecastMillis;
    }

    public ForecastData getForecastData() {
        return forecastData;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Snow getSnow() {
        return snow;
    }

    public Sys getSys() {
        return sys;
    }

    public String getForecastDate() {
        return forecastDate;
    }
}
