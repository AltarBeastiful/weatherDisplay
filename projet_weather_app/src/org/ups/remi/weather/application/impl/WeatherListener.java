package org.ups.remi.weather.application.impl;

import org.osgi.util.tracker.ServiceTracker;
import org.ups.remi.weather.display.IWeatherDisplay;
import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.WeatherType;
import org.ups.remi.weather.provider.IWeatherListener;

public class WeatherListener implements IWeatherListener {
	
	private ServiceTracker<IWeatherDisplay, IWeatherDisplay> displayTracker;
	
	private ILocation currentLocation;
	
	public WeatherListener(ServiceTracker<IWeatherDisplay, IWeatherDisplay> displays, ILocation location) {
		this.displayTracker = displays;
		this.currentLocation = location;
	}

	@Override
	public void weatherChanged(WeatherType weather) {
		System.out.println("Weather changed");
		
		for (IWeatherDisplay weatherDisplay: displayTracker.getServices(new IWeatherDisplay[0])) {			
			weatherDisplay.display(weather, currentLocation);
		}
	}
}
