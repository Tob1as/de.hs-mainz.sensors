package de.hsmainz.sensors.helper;

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