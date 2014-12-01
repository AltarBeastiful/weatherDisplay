package org.ups.remi.weather.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ups.remi.weather.app.IWeatherApplication;
import org.ups.remi.weather.display.IWeatherDisplay;

public class WeatherDisplayTracker implements ServiceTrackerCustomizer<IWeatherDisplay, IWeatherDisplay> {

	private BundleContext bundleContext;

	public WeatherDisplayTracker(BundleContext _bundleContext) {		
		bundleContext = _bundleContext;
	}
	
	@Override
	public IWeatherDisplay addingService(
			ServiceReference<IWeatherDisplay> reference) {
		IWeatherDisplay myService = (IWeatherDisplay) bundleContext.getService(reference);	
		//TODO : Start display bundle from here?
		
		ServiceReference<IWeatherApplication> app = bundleContext.getServiceReference(IWeatherApplication.class);
		if(app != null)
			bundleContext.getService(app).refreshAvailableLocations();
		
		return myService;
	}

	@Override
	public void modifiedService(ServiceReference<IWeatherDisplay> reference,
			IWeatherDisplay service) {
		
	}

	@Override
	public void removedService(ServiceReference<IWeatherDisplay> reference,
			IWeatherDisplay service) {
		// TODO Auto-generated method stub
		
	}

}
