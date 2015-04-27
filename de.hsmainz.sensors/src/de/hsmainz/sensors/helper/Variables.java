package de.hsmainz.sensors.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Variables {
	/*
	 * TODO Variablen für Messwerte mit getters und setters
	 */
	
	private Float humidity, irtemp, pressure, rgb, temp, battery, dewpoint;
	private String macaddress = "0017ec11c070", date, time;
	private Date currentDate = new Date();
	
	public String getTime() {
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		time = formatTime.format(currentDate);
		return time;
	}

	public String getDate() {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
		date = formatDate.format(currentDate);
		return date;
	}
	
	public Float getDewpoint() {
		//dewpoint = (float) 0;
		
		if (getTemp() != null && getHumidity() != null) {
			dewpoint = (float) (getTemp() - (14.55 + 0.114 * getTemp()) * (1 - (0.01 * getHumidity())) - Math.pow(((2.5 + 0.007 * getHumidity()) * (1 - (0.01 * getHumidity()))),3) - (15.9 + 0.117 * getTemp()) * Math.pow((1 - (0.01 * getHumidity())), 14));	
		}
		return dewpoint;
	}

	public String getMacaddress() {
		return macaddress;
	}
	public void setMac(String macaddress) {
		this.macaddress = macaddress;
	}
	public Float getHumidity() {
		return humidity;
	}
	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}
	public Float getIrtemp() {
		return irtemp;
	}
	public void setIrtemp(Float irtemp) {
		this.irtemp = irtemp;
	}
	public Float getPressure() {
		return pressure;
	}
	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}
	public Float getRgb() {
		return rgb;
	}
	public void setRgb(Float rgb) {
		this.rgb = rgb;
	}
	public Float getTemp() {
		return temp;
	}
	public void setTemp(Float temp) {
		this.temp = temp;
	}
	public Float getBattery() {
		return battery;
	}
	public void setBattery(Float battery) {
		this.battery = battery;
	}
	
}