package org.ups.remi.weather.provider.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.WeatherType;
import org.ups.remi.weather.provider.IWeatherListener;
import org.ups.remi.weather.provider.IWeatherService;


public class OpenWeatherProvider extends Thread implements IWeatherService {
	
	private ArrayList<ILocation> locations;
	private Map<ILocation, List<IWeatherListener>> listeners;
	private Map<ILocation, WeatherType> previousWeather;
	
	public OpenWeatherProvider() {
		locations = new ArrayList<ILocation>();
		
		//FIXME: we should provide a meaningfull list of location or let the user enter the coordinates. 
		//This is not the expected behaviour in the current state of the interfaces
		locations.add(new ILocation() {
			
			@Override
			public Float getLongitude() {
				return 23f;
			}
			
			@Override
			public Float getLatitude() {
				return 6f;
			}
		});
		
		locations.add(new ILocation() {
			
			@Override
			public Float getLongitude() {
				return 3f;
			}
			
			@Override
			public Float getLatitude() {
				return 56f;
			}
		});
		
		locations.add(new ILocation() {
			
			@Override
			public Float getLongitude() {
				return 1.5083300f;
			}
			
			@Override
			public Float getLatitude() {
				return 43.5011100f;
			}
		});
		
		listeners = new HashMap<ILocation, List<IWeatherListener>>();
		previousWeather = new HashMap<ILocation, WeatherType>();
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
		
	/**
	 * Updates weather for all locations listened
	 * @author https://github.com/Floki/
	 */
	public void updateWeather() {
		for (ILocation locationService : listeners.keySet()) {

			try {
				URL urlTmp = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+locationService.getLatitude()+"&lon="+locationService.getLongitude());
			     
				BufferedReader in = new BufferedReader(new InputStreamReader(urlTmp.openStream()));
		
		        String stringTmp = in.readLine();
		        String patternStringWeatherId = "\"weather\":\\[\\{\"id\":([0-9]*),";
		
		        Pattern patternWeatherId = Pattern.compile(patternStringWeatherId);
		        Matcher matcherWeather = patternWeatherId.matcher(stringTmp);
		        
		        if(matcherWeather.find()) {
		        	WeatherType newWeather = weatherNameFromId(Integer.parseInt(matcherWeather.group(1)));
					
					if(isWeatherChanged(locationService, newWeather)){
						previousWeather.put(locationService, newWeather);
						
						for (IWeatherListener listener : listeners.get(locationService)) {
							listener.weatherChanged(newWeather);
						}
					}
		        }
		
			}
			catch(Exception e) {
				e.printStackTrace();
			}		
		}
	}
	
	/**
	 * Converts OpenWeatherMap weather code in WeatherType 
	 * 
	 * @param id OpenWeatherMap weather code
	 * @return WeatherType corresponding to the weather
	 * @author https://github.com/Floki/
	 */
	private static WeatherType weatherNameFromId(int id) {
		if(id >= 200 && id < 300) {
			return WeatherType.SHOWERS;
		}
		else if(id >= 300 && id < 600){
			return WeatherType.RAINY;
		}
		else if(id >= 600 && id < 700){
			return WeatherType.SNOW;
		}
		else if((id >= 700 && id < 800) || id > 802) {
			return WeatherType.CLOUDY;
		}
		else if(id >= 800 && id <= 802) {
			return WeatherType.SHINY;
		}
		return WeatherType.UNKNOWN;
	}
	
	private boolean isWeatherChanged(ILocation location, WeatherType newWeather) {
		if(!previousWeather.containsKey(location))
			return true;
		
		return !previousWeather.get(location).equals(newWeather);
	}
	
   public void run() {
        System.out.println("Starting openweatherProvider thread!");
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
