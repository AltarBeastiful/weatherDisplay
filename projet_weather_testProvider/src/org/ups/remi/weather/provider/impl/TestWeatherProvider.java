package org.ups.remi.weather.provider.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		weatherListener.weatherChanged(WeatherType.CLOUDY);
	}
	
   public void run() {
        System.out.println("Starting testweatherProvider thread!");
//        try {
//            while (true) {
//        		this.
//            	Thread.sleep(5 * 1000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String args[]) {
        (new TestWeatherProvider()).start();
    }


}
