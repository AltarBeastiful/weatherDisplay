package org.ups.remi.weather.display.impl;

import org.ups.remi.weather.application.IWeatherDisplay;
import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.WeatherType;

public class ConsoleWeatherDisplay implements IWeatherDisplay{

	@Override
	public void display(WeatherType weather, ILocation location) {
		System.out.println("It's " + weather.name() + " in " + location.getLatitude() + ", " + location.getLongitude());
	}

}
