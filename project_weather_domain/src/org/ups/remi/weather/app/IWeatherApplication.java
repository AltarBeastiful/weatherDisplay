package org.ups.remi.weather.app;

import org.ups.remi.weather.domain.ILocation;


public interface IWeatherApplication {

	public abstract void registerLocation(ILocation location);
	
	public abstract void refreshAvailableLocations();

}