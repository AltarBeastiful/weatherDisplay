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
import org.ups.remi.weather.domain.IWeatherListener;
import org.ups.remi.weather.domain.IWeatherService;
import org.ups.remi.weather.domain.WeatherType;


public class OpenWeatherProvider extends Thread implements IWeatherService {
	
	private ArrayList<ILocation> locations;
	private Map<ILocation, List<IWeatherListener>> listeners;
	private Map<ILocation, WeatherType> previousWeather;
	
	public OpenWeatherProvider() {
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
	
//	private void updateWeather() {
//		for (ILocation iLocation : locations) {
//			for (IWeatherListener listener : getListeners(iLocation)) {
//				listener.weatherChanged(WeatherType.values()[new Random().nextInt(WeatherType.values().length - 1)]);
//			}
//		}
//	}
	
//	private List<IWeatherListener> getListeners(ILocation location) {
//		if(!listeners.containsKey(location)) {
//			return new ArrayList<IWeatherListener>();
//		}
//		
//		return listeners.get(location);
//	}
	
//	/**
//	 * Va chercher le temps
//	 */
//	private void retreiveNewWeather(){
//		System.out.println("Api OpenWeatherMap is going called");
//		
//		
//		for (ILocation locationService : listeners.keySet()) {
//			
//		
//		URL url = null;
//		try {
//			url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+locationService.getLatitude()+"&lon="+locationService.getLongitude());
//		
//			InputStream is = url.openStream();
//			JsonReader rdr = Json.createReader(is);
//					
//			JsonObject obj = rdr.readObject();
//			int previsionId = obj.getJsonArray("weather").getValuesAs(JsonObject.class).get(0).getJsonNumber("id").intValue();
//			WeatherType newWeather = this.convertWeatherFromApi(previsionId);
//			
//			if(isWeatherChanged(locationService, newWeather)){
//				previousWeather.put(locationService, newWeather);
//				
//				for (IWeatherListener listener : listeners.get(locationService)) {
//					listener.weatherChanged(newWeather);
//				}
//			}
//			
//			System.out.println("Id temps trouvé à la location " +locationService.getLatitude()+ " : " + locationService.getLongitude() + " : " + previsionId);
//			
//		} catch (IOException e) {
//			System.out.println("Erreur à l'appel de l'api");
//		}
//		}
//	}
//	
//	/**
//	 * Convertisseur du retour de l'api vers le type utilisé en interne
//	 * @see http://bugs.openweathermap.org/projects/api/wiki/Weather_Data
//	 * @param id l'id du temps retourné par l'api
//	 * @return le type reconnu
//	 */
//	private WeatherType convertWeatherFromApi(int id){
//		
//		WeatherType toReturn;
//		
//		int globalId = (int) Math.floor(id / 100);
//		
//		switch(globalId){
//			case 5: toReturn = WeatherType.SHINY; break;
//			
//			case 6: toReturn = WeatherType.RAINY; break;
//			
//			case 8: toReturn = WeatherType.CLOUDY; break;
//			
//			default: toReturn = WeatherType.UNKNOWN; break;
//		}
//		
//		// Cas particuliers
//		switch(id){
//			case 800: toReturn = WeatherType.SHINY; break;	
//		}
//		return toReturn;
//		
//	}
	
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

	public void updateWeather() {
		for (ILocation locationService : listeners.keySet()) {

			try {
				URL urlTmp = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+locationService.getLatitude()+"&lon="+locationService.getLongitude());
//				System.out.println("URL = " + urlTmp);
			     
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
