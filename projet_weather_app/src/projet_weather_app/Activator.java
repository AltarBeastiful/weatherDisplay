package projet_weather_app;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ups.remi.weather.app.IWeatherApplication;
import org.ups.remi.weather.application.impl.WeatherApplication;
import org.ups.remi.weather.display.IWeatherDisplay;
import org.ups.remi.weather.provider.IWeatherService;
import org.ups.remi.weather.tracker.WeatherDisplayTracker;
import org.ups.remi.weather.tracker.WeatherProviderTracker;

public class Activator implements BundleActivator {

	private ServiceTracker<IWeatherDisplay, IWeatherDisplay> displayTracker;
	private ServiceTracker<IWeatherService, IWeatherService> providerTracker;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting Weather application");
		
		ServiceTrackerCustomizer<IWeatherService, IWeatherService> weatherProviders =
				new WeatherProviderTracker(context);
		providerTracker = new ServiceTracker<IWeatherService, IWeatherService>
		(context, IWeatherService.class.getName(), weatherProviders);
		providerTracker.open();

		ServiceTrackerCustomizer<IWeatherDisplay, IWeatherDisplay> weatherDisplays =
				new WeatherDisplayTracker(context);
		displayTracker = new ServiceTracker<IWeatherDisplay, IWeatherDisplay>
		(context, IWeatherDisplay.class.getName(), weatherDisplays);
		displayTracker.open();

		IWeatherApplication app = new WeatherApplication(displayTracker, providerTracker);
		context.registerService(IWeatherApplication.class.getName(), app, null);
		
		app.refreshAvailableLocations();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
		displayTracker.close();
		providerTracker.close();
	}

}
