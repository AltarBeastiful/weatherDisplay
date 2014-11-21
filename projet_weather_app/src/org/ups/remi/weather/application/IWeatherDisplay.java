package org.ups.remi.weather.application;

import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.WeatherType;


public interface IWeatherDisplay {
	public void display(WeatherType weather, ILocation location);
}
