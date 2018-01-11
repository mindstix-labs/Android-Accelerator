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

import java.util.List;

/**
 * POJO class - Top level class of Forecast API response.
 */
public class Forecast {

    private String cod;

    private Float message;

    @SerializedName("cnt")
    private Integer count;

    @SerializedName("list")
    private List<ForecastList> forecastList = null;

    private City city;

    public String getCod() {
        return cod;
    }

    public Float getMessage() {
        return message;
    }

    public Integer getCount() {
        return count;
    }

    public List<ForecastList> getForecastList() {
        return forecastList;
    }

    public City getCity() {
        return city;
    }
}
