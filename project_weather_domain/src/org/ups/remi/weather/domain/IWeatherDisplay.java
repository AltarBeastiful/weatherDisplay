package org.ups.remi.weather.domain;

import java.util.List;


public interface IWeatherDisplay {
	public void display(WeatherType weather, ILocation location);
	public void display(List<ILocation> locations);
}
