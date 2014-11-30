package org.ups.remi.weather.provider.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.WeatherType;
import org.ups.remi.weather.provider.IWeatherListener;
import org.ups.remi.weather.provider.IWeatherService;


public class TestWeatherProvider extends Thread implements IWeatherService {
	
	private ArrayList<ILocation> locations;
	private Map<ILocation, List<IWeatherListener>> listeners;
	
	public TestWeatherProvider() {
		locations = new ArrayList<ILocation>();
		
		locations.add(new ILocation() {
			
			@Override
			public Float getLongitude() {
				return 1f;
			}
			
			@Override
			public Float getLatitude() {
				return 2f;
			}
		});
		
		listeners = new HashMap<ILocation, List<IWeatherListener>>();
	}

	@Override
	public List<ILocation> getListAvailableLocation() {
		return locations;
	}

	@Override
	public void addWeatherListener(IWeatherListener weatherListener,
			ILocation location) {
		
		if(!listeners.containsKey(location)) {
			listeners.put(location, new ArrayList<IWeatherListener>());
		}
		
		listeners.get(location).add(weatherListener);
		
		updateWeather();
	}
	
	private void updateWeather() {
		for (ILocation iLocation : locations) {
			for (IWeatherListener listener : getListeners(iLocation)) {
				listener.weatherChanged(WeatherType.values()[new Random().nextInt(WeatherType.values().length - 1)]);
			}
		}
	}
	
	private List<IWeatherListener> getListeners(ILocation location) {
		if(!listeners.containsKey(location)) {
			return new ArrayList<IWeatherListener>();
		}
		
		return listeners.get(location);
	}
	
   public void run() {
        System.out.println("Starting testweatherProvider thread!");
        try {
            while (true) {
        		this.updateWeather();
            	Thread.sleep(2 * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
