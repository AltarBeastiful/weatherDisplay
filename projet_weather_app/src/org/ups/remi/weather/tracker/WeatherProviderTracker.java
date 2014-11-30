package org.ups.remi.weather.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ups.remi.weather.domain.IWeatherApplication;
import org.ups.remi.weather.provider.IWeatherService;

public class WeatherProviderTracker implements ServiceTrackerCustomizer<IWeatherService, IWeatherService> {

	private BundleContext bundleContext;

	public WeatherProviderTracker(BundleContext _bundleContext) {		
		bundleContext = _bundleContext;
	}
	
	@Override
	public IWeatherService addingService(
			ServiceReference<IWeatherService> reference) {
		IWeatherService myService = (IWeatherService) bundleContext.getService(reference);
		
		//TODO : Start display bundle from here?
		
		ServiceReference<IWeatherApplication> app = bundleContext.getServiceReference(IWeatherApplication.class);
		if(app != null)
			bundleContext.getService(app).refreshAvailableLocations();
		
		return myService;
	}

	@Override
	public void modifiedService(ServiceReference<IWeatherService> reference,
			IWeatherService service) {

	}

	@Override
	public void removedService(ServiceReference<IWeatherService> reference,
			IWeatherService service) {

	}

}
