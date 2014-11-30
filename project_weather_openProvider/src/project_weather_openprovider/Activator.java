package project_weather_openprovider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ups.remi.weather.domain.IWeatherService;
import org.ups.remi.weather.provider.impl.OpenWeatherProvider;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");
		
		OpenWeatherProvider provider = new OpenWeatherProvider();
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
