package com.wolff.wzagotovka.yahooWeather;

/**
 * Created by wolff on 24.03.2017.
 */

public interface YahooWeatherInfoListener {
    public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType);
}

