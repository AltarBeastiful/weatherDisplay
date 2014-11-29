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
		
		TestWeatherProvider provider = new TestWeatherProvider();
		provider.start();
		
		context.registerService(IWeatherService.class.getName(), provider, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
