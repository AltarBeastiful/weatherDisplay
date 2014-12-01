package project_weather_swtdisplay;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ups.remi.weather.display.impl.SwingWeatherDisplay;
import org.ups.remi.weather.domain.IWeatherDisplay;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");
		
		SwingWeatherDisplay window = new SwingWeatherDisplay();
//		window.getFrame().setVisible(true);
		window.run();
		context.registerService(IWeatherDisplay.class.getName(), window, null);

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
