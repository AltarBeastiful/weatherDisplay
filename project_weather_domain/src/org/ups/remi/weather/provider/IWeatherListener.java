package org.ups.remi.weather.provider;

import org.ups.remi.weather.domain.WeatherType;



public interface IWeatherListener {

	public void weatherChanged(WeatherType wheather);
}
