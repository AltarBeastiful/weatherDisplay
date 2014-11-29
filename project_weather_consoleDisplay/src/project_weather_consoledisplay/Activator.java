package project_weather_consoledisplay;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ups.remi.weather.display.IWeatherDisplay;
import org.ups.remi.weather.display.impl.ConsoleWeatherDisplay;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting Weather display");
		context.registerService(IWeatherDisplay.class.getName(), new ConsoleWeatherDisplay(), null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye from console display!!");
	}

}
