package org.ups.remi.weather.display;

import java.util.List;

import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.WeatherType;


public interface IWeatherDisplay {
	public void display(WeatherType weather, ILocation location);
	public void display(List<ILocation> locations);
}
