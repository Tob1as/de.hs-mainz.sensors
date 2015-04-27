package de.hsmainz.sensors;

import com.sensorcon.sensordrone.DroneEventHandler;
import com.sensorcon.sensordrone.DroneEventObject;
import com.sensorcon.sensordrone.java.Drone;

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
	 * 5. optional: XML: fortführen und speichern weiterer Werte statt immer nur den letzten!
	 * 6. optional: CO2 Sensor integieren
	 * 7. optional: Testbetrieb ermöglichen und Werte generieren, falls Sensordrone nicht verfügbar!
	 */
	
	static Drone drone = new Drone();
	static boolean humidityMeasured = false, irtempMeasured = false, pressureMeasured = false, rgbMeasured = false, tempMeasured = false, batteryMeasured = false;
	static Variables variables = new Variables();
	
	public static void main(String[] args) throws InterruptedException {
		DroneEventHandler myDroneEventHandler = new DroneEventHandler() {
			
			
			@Override
			public void parseEvent(DroneEventObject droneEventObject) {
				if (droneEventObject.matches(DroneEventObject.droneEventType.CONNECTED)) {
					//System.out.println("connected");
					drone.setLEDs(0, 0, 126); // Set LED blue when connected
					//drone.enableADC();
					//drone.enableAltitude();
					//drone.enableCapacitance();
					drone.enableHumidity();
					drone.enableIRTemperature();
					//drone.enableOxidizingGas();
					//drone.enablePrecisionGas();
					drone.enablePressure();
					//drone.enableReducingGas();
					drone.enableRGBC();
					drone.enableTemperature();
					
					drone.measureBatteryVoltage();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.HUMIDITY_ENABLED)) {
					//System.out.println("Humidity is enabled, measure Value!");
					drone.measureHumidity();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.IR_TEMPERATURE_ENABLED)) {
					//System.out.println("irTemperature is enabled, measure Value!");
					drone.measureIRTemperature();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.PRESSURE_ENABLED)) {
					//System.out.println("Pressure is enabled, measure Value!");
					drone.measurePressure();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.RGBC_ENABLED)) {
					//System.out.println("RGB is enabled, measure Value!");
					drone.measureRGBC();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.TEMPERATURE_ENABLED)) {
					//System.out.println("Temperature is enabled, measure Value!");
					drone.measureTemperature();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.HUMIDITY_MEASURED)) {
					variables.setHumidity(drone.humidity_Percent);
					humidityMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.IR_TEMPERATURE_MEASURED)) {
					variables.setIrtemp(drone.irTemperature_Celsius);
					irtempMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.PRESSURE_MEASURED)) {
					variables.setPressure(drone.pressure_Pascals/100); // /100 from Pa to hPa
					pressureMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.RGBC_MEASURED)) {
					variables.setRgb(drone.rgbcLux);
					rgbMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.TEMPERATURE_MEASURED)) {
					variables.setTemp(drone.temperature_Celsius);
					tempMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.BATTERY_VOLTAGE_MEASURED)) {
					//variables.setBattery((float) (drone.batteryVoltage_Volts - 3.1) * 100);
					variables.setBattery((float) (drone.batteryVoltage_Volts));
					batteryMeasured = true;
				}
				//System.out.println(droneEventObject);
			}
		};

		drone.registerDroneListener(myDroneEventHandler);

		if (!drone.isConnected) {
			//System.out.println("Trying to connect Drone with MAC \""+variables.getMacaddress()+"\" !");
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
		if (humidityMeasured&&irtempMeasured&&pressureMeasured&&rgbMeasured&&tempMeasured&&batteryMeasured){
			
			String date = variables.getDate(), time = variables.getTime();
			
			// Ausgabe
			System.out.println(date+"-"+time);
			System.out.println(String.format("%.2f V Batterie", variables.getBattery()));
			//System.out.println("Aufladevorgang: "+ drone.isCharging);
			System.out.println(String.format("%.2f", variables.getHumidity())+" \u0025 Luftfeuchtigkeit");
			System.out.println(String.format("%.2f \u00B0C Infrarot Temperatur", variables.getIrtemp()));
			System.out.println(String.format("%.2f \u00B0C Temperatur", variables.getTemp()));
			System.out.println(String.format("%.2f \u00B0C Taupunkt", variables.getDewpoint()));
			System.out.println(String.format("%.2f hPa Luftdruck", variables.getPressure()));
			System.out.println(String.format("%.2f lux Helligkeit", variables.getRgb()));
			
			// XML 
			XML xml = new XML();
			xml.writeMeasureInXML(date,time,variables.getHumidity(),variables.getIrtemp(),variables.getPressure(),variables.getRgb(),variables.getTemp(),variables.getBattery());
			
			// SQL
			SQL sql = new SQL();
			sql.saveMeasureInSQL(date,time,variables.getHumidity(),variables.getIrtemp(),variables.getPressure(),variables.getRgb(),variables.getTemp(),variables.getBattery());
		}
		if (i >= 10) {
			//drone.disableADC();
			//drone.disableAltitude();
			//drone.disableCapacitance();
			//drone.disableHumidity();
			//drone.disableIRTemperature();
			//drone.disableOxidizingGas();
			//drone.disablePrecisionGas();
			//drone.disablePressure();
			//drone.disableReducingGas();
			//drone.disableRGBC();
			//drone.disableTemperature();
			
			drone.disconnect();
		}
	}
}