package de.hsmainz.sensors;


import com.sensorcon.sensordrone.DroneEventHandler;
import com.sensorcon.sensordrone.DroneEventObject;
import com.sensorcon.sensordrone.java.Drone;

import de.hsmainz.sensors.helper.Variables;


public class Sensordrone implements ISensor {
	
	/*
	 * Sensordrone http://sensorcon.com/
	 */
	
	private Drone drone = new Drone();
	private boolean batteryMeasured = false, altitudeMeasured = false, capacitanceMeasured = false, humidityMeasured = false, irtemperatureMeasured = false, oxidizinggasMeasured = false, precisiongasMeasured = false, pressureMeasured = false, reducinggasMeasured = false, rgbMeasured = false, temperatureMeasured = false;
	
	private Variables variables = new Variables();

	public Sensordrone(Variables variables) {
		this.variables = variables;
	}

	public void execute() throws InterruptedException {
		DroneEventHandler myDroneEventHandler = new DroneEventHandler() {
			@Override
			public void parseEvent(DroneEventObject droneEventObject) {
				if (droneEventObject.matches(DroneEventObject.droneEventType.CONNECTED)) {
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
					measureCO2();
				// Enabled
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.ALTITUDE_ENABLED)) {
					drone.measureAltitude();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.CAPACITANCE_ENABLED)) {
					drone.measureCapacitance();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.HUMIDITY_ENABLED)) {
					drone.measureHumidity();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.IR_TEMPERATURE_ENABLED)) {
					drone.measureIRTemperature();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.OXIDIZING_GAS_ENABLED)) {
					drone.measureOxidizingGas();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.PRECISION_GAS_ENABLED)) {
					drone.measurePrecisionGas();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.PRESSURE_ENABLED)) {
					drone.measurePressure();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.REDUCING_GAS_ENABLED)) {
					drone.measureReducingGas();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.RGBC_ENABLED)) {
					drone.measureRGBC();
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.TEMPERATURE_ENABLED)) {
					drone.measureTemperature();
				// Measured
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.BATTERY_VOLTAGE_MEASURED)) {
					variables.setBattery((float) (drone.batteryVoltage_Volts));
					batteryMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.ALTITUDE_MEASURED)) {
					// drone.altitude_Meters, drone.altitude_Feet
					variables.setAltitude(drone.altitude_Meters);
					altitudeMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.CAPCACITANCE_MEASURED)) {
					variables.setCapacitance(drone.capacitance_femtoFarad);
					capacitanceMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.HUMIDITY_MEASURED)) {
					variables.setHumidity(drone.humidity_Percent);
					humidityMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.IR_TEMPERATURE_MEASURED)) {
					// drone.irTemperature_Celsius, drone.irTemperature_Fahrenheit, drone.irTemperature_Kelvin
					variables.setIrtemperature(drone.irTemperature_Celsius);
					irtemperatureMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.OXIDIZING_GAS_MEASURED)) {
					variables.setOxidizinggas(drone.oxidizingGas_Ohm);
					oxidizinggasMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.PRECISION_GAS_MEASURED)) {
					variables.setPrecisiongas(drone.precisionGas_ppmCarbonMonoxide);
					precisiongasMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.PRESSURE_MEASURED)) {
					// drone.pressure_Pascals, drone.pressure_Atmospheres, drone.pressure_Torr
					variables.setPressure(drone.pressure_Pascals);
					pressureMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.REDUCING_GAS_MEASURED)) {
					variables.setReducinggas(drone.reducingGas_Ohm);
					reducinggasMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.RGBC_MEASURED)) {
					// drone.rgbcLux, drone.rgbcColorTemperature, drone.rgbcClearChannel, rgbcBlueChannel, rgbcGreenChannel, rgbcRedChannel
					variables.setRgb(drone.rgbcLux);
					rgbMeasured = true;
				} else if (droneEventObject.matches(DroneEventObject.droneEventType.TEMPERATURE_MEASURED)) {
					// drone.temperature_Celsius, drone.temperature_Fahrenheit, drone.temperature_Kelvin
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
			System.out.println("Connection Failed!");
		}

		int i = 0; 
		while (drone.isConnected && (i < 200 || !batteryMeasured || !altitudeMeasured || !capacitanceMeasured || !humidityMeasured || !irtemperatureMeasured || !oxidizinggasMeasured || !precisiongasMeasured || !pressureMeasured || !reducinggasMeasured || !rgbMeasured || !temperatureMeasured)) {
			Thread.sleep(20);
			i++;
		}
		
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
	
	public Variables getVariables(){
		return this.variables;
	}
	
	private void measureCO2() {
		// CO2 Sensor http://www.mb-systemtechnik.de/produkte_co2_messung_co2_sensor_modul_mit_ausgang.htm
		// Set baudrate
		drone.setBaudRate_38400();
		// Command
		byte[] getStatusCommand = { 0x23, 0x31, 0x30, 0x0D }; // 10 (Read status)
		// UART Write/Read
		byte[] response = drone.uartWriteForRead(getStatusCommand, 5);
		variables.setCo2(variables.getFloatValueFromUartResponse(response));
	}
}