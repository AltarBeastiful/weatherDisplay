package org.ups.remi.weather.domain;


public interface IWeatherApplication {

	public abstract void registerLocation(ILocation location);
	
	public abstract void refreshAvailableLocations();

}