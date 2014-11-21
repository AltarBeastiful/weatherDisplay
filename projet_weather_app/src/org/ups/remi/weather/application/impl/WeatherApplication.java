package org.ups.remi.weather.application.impl;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.ups.remi.weather.application.IWeatherDisplay;
import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.WeatherType;
import org.ups.remi.weather.provider.IWeatherListener;
import org.ups.remi.weather.provider.IWeatherService;

public class WeatherApplication implements IWeatherListener {

	private ServiceTracker<IWeatherDisplay, IWeatherDisplay> displayTracker;
	private ServiceTracker<IWeatherService, IWeatherService> providerTracker;
	
	private ILocation currentLocation;

	public WeatherApplication(
			ServiceTracker<IWeatherDisplay, IWeatherDisplay> displayTracker,
			ServiceTracker<IWeatherService, IWeatherService> providerTracker) {
		this.displayTracker = displayTracker;
		this.providerTracker = providerTracker;
		
		this.currentLocation = null;
	}

	@Override
	public void weatherChanged(WeatherType weather) {
		System.out.println("Weather changed");
		
		//TODO : display weather on all available displays
		displayTracker.getService().display(weather, currentLocation);
	}
	
	public void registerLocation(ILocation location) {
		if(providerTracker.getService().getListAvailableLocation().contains(location)) {
			providerTracker.getService().addWeatherListener(this, location);
			currentLocation = location;
		}
	}
	
//	public static void main(String[] args) {
//		String[] equinoxArgs = {"-console", "1234","-noExit"};
//	    BundleContext context;
//		try {
//			context = EclipseStarter.startup(equinoxArgs,null);
//
//		    Bundle bundle1 = context.installBundle("file:/tmp/osgi/plugins/projet_weather_testProvider_1.0.0.201411201613.jar");
//		    Bundle bundle2 = context.installBundle("file:/tmp/osgi/plugins/projet_weather_app_1.0.0.201411201613.jar");
//		    Bundle bundle3 = context.installBundle("file:/tmp/osgi/plugins/project_weather_domain_1.0.0.201411201613.jar");
//		    Bundle bundle4 = context.installBundle("file:/tmp/osgi/plugins/project_weather_consoleDisplay_1.0.0.201411201613.jar");
//		    
//		    bundle3.start();
//		    bundle1.start();
//		    bundle4.start();
//		    bundle2.start();
//		    
//		    System.out.println("finish starting");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
