package org.ups.remi.weather.display.impl;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.ups.remi.weather.domain.ILocation;

public class LocationCellRenderer extends JLabel implements
		ListCellRenderer<ILocation> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends ILocation> list, ILocation value, int index,
			boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		setText(value.getLatitude() + ", " + value.getLongitude());
		return this;
	}

}
