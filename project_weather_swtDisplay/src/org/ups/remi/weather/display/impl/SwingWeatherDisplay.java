package org.ups.remi.weather.display.impl;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.ups.remi.weather.display.IWeatherDisplay;
import org.ups.remi.weather.domain.ILocation;
import org.ups.remi.weather.domain.IWeatherApplication;
import org.ups.remi.weather.domain.WeatherType;

public class SwingWeatherDisplay  extends Thread implements IWeatherDisplay{

	private JFrame frame;
	
	private JList<ILocation> locationList;
	private JList<WeatherLocation> weatherList;
	
	private DefaultListModel<ILocation> locationModel;
	private DefaultListModel<WeatherLocation> weatherModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingWeatherDisplay window = new SwingWeatherDisplay();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingWeatherDisplay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 450, 300);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		locationList = new JList<ILocation>();
		getFrame().getContentPane().add(locationList);
		
		locationModel = new DefaultListModel<ILocation>();
		locationList.setModel(locationModel);
		locationList.setCellRenderer(new LocationCellRenderer());
		locationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass())
						.getBundleContext();
				
				ServiceReference<IWeatherApplication> app = bundleContext.getServiceReference(IWeatherApplication.class);
				if(app != null)
					bundleContext.getService(app).registerLocation((ILocation) locationList.getSelectedValue());	
			}
		});
		
		weatherList = new JList<WeatherLocation>();
		getFrame().getContentPane().add(weatherList);
		weatherModel  = new DefaultListModel<WeatherLocation>();
		weatherList.setModel(weatherModel);
		
		JLabel label = new JLabel("");
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		frame.getContentPane().add(label_1);
		getFrame().getContentPane().add(registerButton);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass())
						.getBundleContext();
				
				ServiceReference<IWeatherApplication> app = bundleContext.getServiceReference(IWeatherApplication.class);
				if(app != null)
					bundleContext.getService(app).refreshAvailableLocations();	
			}
		});
		frame.getContentPane().add(btnRefresh);
	}

	@Override
	public void display(WeatherType weather, ILocation location) {
		WeatherLocation item = new WeatherLocation(weather, location);
		
		boolean found = false;
		for (int i = 0; i < weatherModel.size(); i++) {
			if(weatherModel.getElementAt(i).location.equals(location)){
				weatherModel.add(i, item);
				found = true;
			}
		}
		
		if(!found){
			weatherModel.addElement(item);
		}
	}

	@Override
	public void display(List<ILocation> locations) {
		locationModel.clear();
		for (ILocation iLocation : locations) {
			locationModel.addElement(iLocation);
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void run() {
		this.getFrame().setVisible(true);
	}
	
	
	private class WeatherLocation {
		public WeatherType weather;
		public ILocation location;
		
		public WeatherLocation(WeatherType weather, ILocation location) {
			super();
			this.weather = weather;
			this.location = location;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return weather.toString() + " at " + location.getLatitude() + ", " + location.getLongitude();
		}
	}
}
