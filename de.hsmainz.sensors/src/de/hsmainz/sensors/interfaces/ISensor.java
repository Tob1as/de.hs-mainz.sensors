package de.hsmainz.sensors.interfaces;

import de.hsmainz.sensors.helper.Variables;

public interface ISensor {
	
	/*
	 *  Interface Sensors
	 */
	
	public void execute() throws InterruptedException;
	public Variables getVariables();

}
