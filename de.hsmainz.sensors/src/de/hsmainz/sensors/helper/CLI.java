package de.hsmainz.sensors.helper;

public class CLI {

	public void showCommandLine(Variables variables) {
		System.out.println(isNull(variables.getId()) ? "Hallo Welt"
				: variables.getId());
		System.out.println(isNull(variables.getDate()) ? "Hallo Welt"
				: variables.getDate() + "-" + variables.getTime());
		System.out.println(String.format("%.2f V Batterie",
				variables.getBattery()));
		System.out.println(String.format("%.2f", variables.getHumidity())
				+ " \u0025 Luftfeuchtigkeit");
		System.out.println(String.format("%.2f \u00B0C Infrarot Temperatur",
				variables.getIrtemperature()));
		System.out
				.println(isNull(variables.getTemperature()) ? "FAIL Temperature"
						: String.format("%.2f \u00B0C Temperatur",
								variables.getTemperature()));
		System.out.println(String.format("%.2f \u00B0C Taupunkt",
				variables.getDewpoint()));
		System.out.println(String.format("%.2f hPa Luftdruck",
				variables.getPressure()));
		System.out.println(String.format("%.2f lux Helligkeit",
				variables.getRgb()));
		System.out.println(variables.getAltitude() + " Altitude");
		System.out.println(variables.getCapacitance() + " Capacitance");
		System.out.println(String.format("%.2f KOhms Oxidizing Gas",
				variables.getOxidizinggas()));
		System.out.println(variables.getPrecisiongas() + " Precision Gas");
		System.out.println(String.format("%.2f KOhms Reducing Gas",
				variables.getReducinggas()));
	}

	private boolean isNull(Object a) {
		return a == null;
	}
}