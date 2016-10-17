package de.hs_mainz.Sensors.interfaces;

import de.hs_mainz.Sensors.helper.Variables;

public interface ISensor {
	
	/*
	 *  Interface Sensors
	 */
	
	public void execute() throws InterruptedException;
	public Variables getVariables();

}
