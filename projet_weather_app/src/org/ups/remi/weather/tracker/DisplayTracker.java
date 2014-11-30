package org.ups.remi.weather.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ups.remi.weather.display.IWeatherDisplay;
import org.ups.remi.weather.domain.IWeatherApplication;

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
