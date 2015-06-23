package de.hsmainz.sensors.outputs;

import de.hsmainz.sensors.helper.Variables;
import de.hsmainz.sensors.interfaces.Output;

public class UploadHTTP implements Output{

	/*
	 * Upload measure via http (php script into Database, when no direct connecten)
	 */
	
	@Override
	public void write(Variables variables) {
		this.uploadMeasure(variables);
	}
	
	private void uploadMeasure(Variables variables) {
		// TODO
	}
}