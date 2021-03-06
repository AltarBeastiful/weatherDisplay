package org.ups.remi.weather.application.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.ups.remi.weather.app.IWeatherApplication;
import org.ups.remi.weather.display.IWeatherDisplay;
import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.provider.IWeatherListener;
import org.ups.remi.weather.provider.IWeatherService;

public class WeatherApplication implements IWeatherApplication {

	private ServiceTracker<IWeatherDisplay, IWeatherDisplay> displayTracker;
	private ServiceTracker<IWeatherService, IWeatherService> providerTracker;
	
	public WeatherApplication(
			ServiceTracker<IWeatherDisplay, IWeatherDisplay> displayTracker,
			ServiceTracker<IWeatherService, IWeatherService> providerTracker) {
		this.displayTracker = displayTracker;
		this.providerTracker = providerTracker;
	}
	
	@Override
	/**
	 * Register weather changes against the provider containing the location
	 * 
	 */
	public void registerLocation(ILocation location) {
		for (IWeatherService provider : providerTracker.getServices(new IWeatherService[0])) {	
			
			if(provider.getListAvailableLocation().contains(location)) {
				
				IWeatherListener listener = new WeatherListener(displayTracker, location);
				provider.addWeatherListener(listener, location);
				
				return;
			}
		}
	}

	@Override
	public void refreshAvailableLocations() {
		List<ILocation> availableLocations = new ArrayList<ILocation>();
		for (IWeatherService provider : providerTracker.getServices(new IWeatherService[0])) {
			availableLocations.addAll(provider.getListAvailableLocation());
		}
		
		for (IWeatherDisplay display : displayTracker.getServices(new IWeatherDisplay[0])) {
			display.display(availableLocations);
		}
	}
	
	public static void main(String[] args) {
	    BundleContext context;
		try {
			ServiceLoader<FrameworkFactory> loader = ServiceLoader.load(FrameworkFactory.class);
			FrameworkFactory ff = (FrameworkFactory) loader.iterator().next();
			Map<String,String> config = new HashMap<String,String>();
			config.put("osgi.console.enable.builtin", "true");
			Framework fwk = ff.newFramework(config);
			fwk.start();
			
			context = fwk.getBundleContext();
			
			System.out.println("bundles installed :" + context.getBundles().length);
			for (Bundle bundle : context.getBundles()) {
				System.out.println(bundle.getSymbolicName());
				if(bundle.getLocation().startsWith("file:/tmp/osgi/plugins/")){
					bundle.uninstall();
					System.out.println("Uninstalled");
				}
			}

			Bundle bundle5 = context.installBundle("file:/tmp/osgi/plugins/project_weather_openProvider_1.0.0.201411201613.jar");
			Bundle bundle1 = context.installBundle("file:/tmp/osgi/plugins/projet_weather_testProvider_1.0.0.201411201613.jar");
		    Bundle bundle2 = context.installBundle("file:/tmp/osgi/plugins/projet_weather_app_1.0.0.201411201613.jar");
		    Bundle bundle4 = context.installBundle("file:/tmp/osgi/plugins/project_weather_consoleDisplay_1.0.0.201411201613.jar");
		    Bundle bundle6 = context.installBundle("file:/tmp/osgi/plugins/project_weather_swtDisplay_1.0.0.201411201613.jar");
		    Bundle bundle3 = context.installBundle("file:/tmp/osgi/plugins/project_weather_domain_1.0.0.201411201613.jar");
		    
		    bundle2.start();
		    bundle1.start();
		    bundle4.start();
		    bundle5.start();
		    bundle6.start();
		    
		    System.out.println("finish starting");
		    
		    fwk.waitForStop(0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    System.exit(0);
		}
	}
}
