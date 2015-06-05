package de.hsmainz.sensors;

import de.hsmainz.sensors.helper.Variables;

public interface ISensor {
	
	public void execute() throws InterruptedException;
	public Variables getVariables();

}
