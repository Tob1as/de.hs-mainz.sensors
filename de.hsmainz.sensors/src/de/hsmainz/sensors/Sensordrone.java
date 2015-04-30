package de.hsmainz.sensors;

import com.sensorcon.sensordrone.DroneEventHandler;
import com.sensorcon.sensordrone.DroneEventObject;
import com.sensorcon.sensordrone.java.Drone;

import de.hsmainz.sensors.helper.CLI;
import de.hsmainz.sensors.helper.SQL;
import de.hsmainz.sensors.helper.Variables;
import de.hsmainz.sensors.helper.XML;

public class Sensordrone {
	
	/* TODO Allgemein!
	 * 1. Erstelle config mit Beispieldaten, wenn nicht existiert 
	 * 1.1.1 http://www.mkyong.com/java/java-properties-file-examples/
	 * 1.1.2 http://de.wikipedia.org/wiki/Java-Properties-Datei
	 * 2. Lade config mit settings zu pfaden, speicher SQL/XML true/false, Datenbanksettings usw.
	 * 3. Erstelle Datenbank, falls Sie nicht existiert. (Settings aus config)
	 * 3.1 Schreibe Werte in MySQL-Datenbank (optional: wahl zwischen MySQL, PostgreSQL und SQLite)
	 * 4. Lese Daten via php script aus (vorzugsweise aus Datenbank, optional mit generierten Grafik)!
	 * 5. optional: XML: fortf�hren und speichern weiterer Werte statt immer nur den letzten!
	 * 6. optional: CO2 Sensor integieren
	 * 7. optional: Testbetrieb erm�glichen und Werte generieren, falls Sensordrone nicht verf�gbar!
	 */
	
	private Drone drone = new Drone();
	private boolean batteryMeasured = false, altitudeMeasured = false,
			capacitanceMeasured = false, humidityMeasured = false,
			irtemperatureMeasured = false, oxidizinggasMeasured = false,
			precisiongasMeasured = false, pressureMeasured = false,
			reducinggasMeasured = false, rgbMeasured = false,
			temperatureMeasured = false;
	
	private Variables variables = new Variables();

	public void execute() throws InterruptedException {
		DroneEventHandler myDroneEventHandler = new DroneEventHandler() {
			@Override
			public void parseEvent(DroneEventObject droneEventObject) {
				if (droneEventObject
						.matches(DroneEventObject.droneEventType.CONNECTED)) {
					drone.setLEDs(0, 0, 126); // Set LED blue when connected
					// drone.enableADC();
					drone.enableAltitude();
					drone.enableCapacitance();
					drone.enableHumidity();
					drone.enableIRTemperature();
					drone.enableOxidizingGas();
					drone.enablePrecisionGas();
					drone.enablePressure();
					drone.enableReducingGas();
					drone.enableRGBC();
					drone.enableTemperature();

					drone.measureBatteryVoltage();
					// Enabled
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.ALTITUDE_ENABLED)) {
					drone.measureAltitude();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.CAPACITANCE_ENABLED)) {
					drone.measureCapacitance();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.HUMIDITY_ENABLED)) {
					drone.measureHumidity();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.IR_TEMPERATURE_ENABLED)) {
					drone.measureIRTemperature();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.OXIDIZING_GAS_ENABLED)) {
					drone.measureOxidizingGas();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.PRECISION_GAS_ENABLED)) {
					drone.measurePrecisionGas();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.PRESSURE_ENABLED)) {
					drone.measurePressure();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.REDUCING_GAS_ENABLED)) {
					drone.measureReducingGas();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.RGBC_ENABLED)) {
					drone.measureRGBC();
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.TEMPERATURE_ENABLED)) {
					drone.measureTemperature();
					// Measured
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.BATTERY_VOLTAGE_MEASURED)) {
					variables.setBattery((float) (drone.batteryVoltage_Volts));
					batteryMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.ALTITUDE_MEASURED)) {
					variables.setAltitude(drone.altitude_Meters);
					// variables.setAltitude(drone.altitude_Feet);
					altitudeMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.CAPCACITANCE_MEASURED)) {
					variables.setCapacitance(drone.capacitance_femtoFarad);
					capacitanceMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.HUMIDITY_MEASURED)) {
					variables.setHumidity(drone.humidity_Percent);
					humidityMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.IR_TEMPERATURE_MEASURED)) {
					variables.setIrtemp(drone.irTemperature_Celsius);
					irtemperatureMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.OXIDIZING_GAS_MEASURED)) {
					variables.setOxidizinggas(drone.oxidizingGas_Ohm);
					oxidizinggasMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.PRECISION_GAS_MEASURED)) {
					variables
							.setPrecisiongas(drone.precisionGas_ppmCarbonMonoxide);
					precisiongasMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.PRESSURE_MEASURED)) {
					variables.setPressure(drone.pressure_Pascals);
					pressureMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.REDUCING_GAS_MEASURED)) {
					variables.setReducinggas(drone.reducingGas_Ohm);
					reducinggasMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.RGBC_MEASURED)) {
					variables.setRgb(drone.rgbcLux);
					rgbMeasured = true;
				} else if (droneEventObject
						.matches(DroneEventObject.droneEventType.TEMPERATURE_MEASURED)) {
					variables.setTemperature(drone.temperature_Celsius);
					temperatureMeasured = true;
				}
				// System.out.println(droneEventObject);
			}
		};

		drone.registerDroneListener(myDroneEventHandler);

		if (!drone.isConnected) {
			drone.btConnect(variables.getMacaddress());
		}
		if (!drone.isConnected) {
			System.out.println("Not connected!");
		}
		int i = 0;
		while (drone.isConnected && i < 200) {
			Thread.sleep(20);
			i++;
		}
		if (batteryMeasured && altitudeMeasured && capacitanceMeasured
				&& humidityMeasured && irtemperatureMeasured
				&& oxidizinggasMeasured && precisiongasMeasured
				&& pressureMeasured && reducinggasMeasured
				&& temperatureMeasured) {

			// Command Line
			CLI cli = new CLI();
			cli.showCommandLine(variables);

			// XML
			XML xml = new XML();
			xml.writeMeasureInXML(variables.getDate(), variables.getTime(),
					variables.getHumidity(), variables.getIrtemperature(),
					variables.getPressure(), variables.getRgb(),
					variables.getTemperature(), variables.getBattery());

			// SQL
			SQL sql = new SQL();
			sql.saveMeasureInSQL(variables.getDate(), variables.getTime(),
					variables.getHumidity(), variables.getIrtemperature(),
					variables.getPressure(), variables.getRgb(),
					variables.getTemperature(), variables.getBattery());
		}
		if (i >= 10) {
			// drone.disableADC();
			// drone.disableAltitude();
			// drone.disableCapacitance();
			// drone.disableHumidity();
			// drone.disableIRTemperature();
			// drone.disableOxidizingGas();
			// drone.disablePrecisionGas();
			// drone.disablePressure();
			// drone.disableReducingGas();
			// drone.disableRGBC();
			// drone.disableTemperature();

			drone.disconnect();
		}
	}
}