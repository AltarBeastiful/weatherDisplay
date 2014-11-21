package org.ups.remi.weather.provider;

import java.util.List;

import org.ups.remi.weather.domain.ILocation;

public interface IWeatherService {

	public void addWeatherListener(IWeatherListener weatherListener, ILocation location);
	
	public List<ILocation> getListAvailableLocation();
}
