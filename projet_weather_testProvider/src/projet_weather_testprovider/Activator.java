package projet_weather_testprovider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ups.remi.weather.provider.IWeatherService;
import org.ups.remi.weather.provider.impl.TestWeatherProvider;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting Weather provider");
		context.registerService(IWeatherService.class.getName(), new TestWeatherProvider(), null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
