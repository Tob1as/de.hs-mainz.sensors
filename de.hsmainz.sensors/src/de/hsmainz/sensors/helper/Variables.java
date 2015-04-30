package de.hsmainz.sensors.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Variables {
	
	private Float battery, altitude, capacitance, humidity, irtemperature, oxidizinggas, precisiongas, pressure, reducinggas , rgb, temperature;
	private String macaddress = "0017ec11c070";
	private Date currentDate = new Date();
	
	/*
	 * Date and Time
	 */	
	
	public String getId(){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatDate.format(currentDate);
	}
	public String getTime() {
		SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss");
		return formatDate.format(currentDate);
	}
	public String getDate() {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
		return formatDate.format(currentDate);
	}
	
	/*
	 * Drone
	 */

	public String getMacaddress() {
		return macaddress;
	}
	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}
	public Float getBattery() {
		return battery;
	}
	public void setBattery(Float battery) {
		this.battery = battery;
	}
	public Float getAltitude() {
		return altitude;
	}
	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}
	public Float getCapacitance() {
		return capacitance;
	}
	public void setCapacitance(Float capacitance) {
		this.capacitance = capacitance;
	}
	public Float getHumidity() {
		return humidity;
	}
	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}
	public Float getIrtemperature() {
		return irtemperature;
	}
	public void setIrtemp(Float irtemperature) {
		this.irtemperature = irtemperature;
	}
	public Float getOxidizinggas() {
		return oxidizinggas;
	}
	public void setOxidizinggas(Float oxidizinggas) {
		this.oxidizinggas = oxidizinggas/1000; // divided by 1000 to convert Ohm to KOhms
	}
	public Float getPrecisiongas() {
		return precisiongas;
	}
	public void setPrecisiongas(Float precisiongas) {
		this.precisiongas = precisiongas;
	}
	public Float getPressure() {
		return pressure;
	}
	public void setPressure(Float pressure) {
		this.pressure = pressure/100; // divided by 100 to convert Ph to hPa
	}
	public Float getReducinggas() {
		return reducinggas;
	}
	public void setReducinggas(Float reducinggas) {
		this.reducinggas = reducinggas/1000; // divided by 1000 to convert Ohm to KOhms
	}
	public Float getRgb() {
		return rgb;
	}
	public void setRgb(Float rgb) {
		this.rgb = rgb;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	
	/*
	 * Calculations
	 */
	
	public Float getDewpoint() {		
		if (getTemperature() != null && getHumidity() != null) {
			return (float) (getTemperature() - (14.55 + 0.114 * getTemperature()) * (1 - (0.01 * getHumidity())) - Math.pow(((2.5 + 0.007 * getHumidity()) * (1 - (0.01 * getHumidity()))),3) - (15.9 + 0.117 * getTemperature()) * Math.pow((1 - (0.01 * getHumidity())), 14));	
		}
		return null;
	}
}