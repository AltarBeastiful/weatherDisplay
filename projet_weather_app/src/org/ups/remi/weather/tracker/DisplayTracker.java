package org.ups.remi.weather.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ups.remi.weather.application.IWeatherDisplay;

public class DisplayTracker implements ServiceTrackerCustomizer<IWeatherDisplay, IWeatherDisplay> {

	private BundleContext bundleContext;

	public DisplayTracker(BundleContext _bundleContext) {		
		bundleContext = _bundleContext;
	}
	
	@Override
	public IWeatherDisplay addingService(
			ServiceReference<IWeatherDisplay> reference) {
		IWeatherDisplay myService = (IWeatherDisplay) bundleContext.getService(reference);	
		//TODO : Start display bundle from here?
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