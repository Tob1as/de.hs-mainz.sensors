package de.hsmainz.sensors.outputs;

import de.hsmainz.sensors.helper.Variables;
import de.hsmainz.sensors.interfaces.Output;

public class CLI implements Output {

	/*
	 * Show Measurements in CLI (Command Line Interface)
	 */
	
	@Override
	public void write(Variables variables) {
		this.showCommandLine(variables);
	}
	
	private void showCommandLine(Variables variables) {
		// System.out.println(isNull(variables.getId()) ? "Test" : variables.getId());
		System.out.println("ID: " + variables.getId());
		System.out.println("Date: " + variables.getDate());
		System.out.println("Time: " + variables.getTime());
		System.out.println(String.format("%.2f V Battery", variables.getBattery()));
		System.out.println(String.format("%.2f", variables.getHumidity()) + " \u0025 Humidity");
		System.out.println(String.format("%.2f \u00B0C Infrared Temperature", variables.getIrtemperature()));
		System.out.println(String.format("%.2f \u00B0C Temperature", variables.getTemperature()));
		System.out.println(String.format("%.2f \u00B0C Dew Point", variables.getDewpoint()));
		System.out.println(String.format("%.2f hPa Air Pressure", variables.getPressure()));
		System.out.println(String.format("%.2f lux Luminosity", variables.getRgb()));
		System.out.println(variables.getAltitude() + " Altitude");
		System.out.println(variables.getCapacitance() + " Capacitance");
		System.out.println(String.format("%.2f KOhms Oxidizing Gas", variables.getOxidizinggas()));
		System.out.println(variables.getPrecisiongas() + " Precision Gas");
		System.out.println(String.format("%.2f KOhms Reducing Gas", variables.getReducinggas()));
		System.out.println(String.format("%.2f ppm CO2", variables.getCo2()));
	}

	//private boolean isNull(Object a) { return a == null; }

}