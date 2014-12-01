package org.ups.remi.weather.display.impl;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.IWeatherApplication;
import org.ups.remi.weather.domain.IWeatherDisplay;
import org.ups.remi.weather.domain.WeatherType;

public class ConsoleWeatherDisplay implements IWeatherDisplay{

	@Override
	public void display(WeatherType weather, ILocation location) {
		System.out.println("It's " + weather.name() + " in " + location.getLatitude() + ", " + location.getLongitude());
	}

	@Override
	public void display(List<ILocation> locations) {
		//NOTE : register first one instead of letting user choose
		
		if(!locations.isEmpty()) {
			BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass())
					.getBundleContext();
			
			ServiceReference<IWeatherApplication> app = bundleContext.getServiceReference(IWeatherApplication.class);
			if(app != null)
				bundleContext.getService(app).registerLocation(locations.get(0));			
		}
	}

}
