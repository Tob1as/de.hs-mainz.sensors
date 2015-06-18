package de.hsmainz.sensors.helper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Variables {
	
	/*
	 * Variables
	 */
	
	private Float battery, altitude, capacitance, humidity, irtemperature, oxidizinggas, precisiongas, pressure, reducinggas, rgb, temperature, co2;
	private String macaddress = "0017ec11c070", sqldatabase = "sqlite", sqlhost = "localhost", sqlport, sqltable = "measure", sqluser, sqlpassword, uploadHTTPurl="http://127.0.0.1/sensors.php";
	private Date currentDate = new Date();
	private File filenameXML = new File("sensors.xml"), filenameSQLite = new File("sensors.sqlite"), filenameConfig = new File("config.xml");
	private boolean measuredSensorDrone=true, showCLI=true, writeXML=true, saveSQL=true, uploadHTTP=false;

	/*
	 * Date and Time
	 */

	public String getId() {
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
	 * Config settings
	 */
	
	public File getFilenameConfig() {
		return filenameConfig;
	}

	public void setFilenameConfig(File filenameConfig) {
		this.filenameConfig = filenameConfig;
	}

	public boolean isMeasuredSensorDrone() {
		return measuredSensorDrone;
	}

	public void setMeasuredSensorDrone(boolean measuredSensorDrone) {
		this.measuredSensorDrone = measuredSensorDrone;
	}
	
	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}
	
	public boolean isShowCLI() {
		return showCLI;
	}

	public void setShowCLI(boolean showCLI) {
		this.showCLI = showCLI;
	}

	public boolean isWriteXML() {
		return writeXML;
	}

	public void setWriteXML(boolean writeXML) {
		this.writeXML = writeXML;
	}
	
	public File getFilenameXML() {
		return filenameXML;
	}

	public void setFilenameXML(File filenameXML) {
		if (filenameXML != null && filenameXML != new File("")){
			this.filenameXML = filenameXML;
		} 
	}

	public boolean isSaveSQL() {
		return saveSQL;
	}

	public void setSaveSQL(boolean saveSQL) {
		this.saveSQL = saveSQL;
	}
	
	public String getSqldatabase() {
		return sqldatabase;
	}

	public void setSqldatabase(String sqldatabase) {
		if (sqldatabase == "sqlite" || sqldatabase == "mysql" || sqldatabase == "postgresql"){
			this.sqldatabase = sqldatabase;
		} 
	}
	
	public File getFilenameSQLite() {
		return filenameSQLite;
	}

	public void setFilenameSQLite(File filenameSQLite) {
		if (filenameSQLite != null && filenameSQLite != new File("")){
			this.filenameSQLite = filenameSQLite;
		} 		
	}
	
	public String getSqlhost() {
		return sqlhost;
	}

	public void setSqlhost(String sqlhost) {
		if (sqlhost!=null && sqlhost.isEmpty()){
			this.sqlhost = sqlhost;
		}
	}

	public String getSqlport() {
		return sqlport;
	}

	public void setSqlport(String sqlport) {
		if (sqlport!=null && sqlport.isEmpty()){
			this.sqlport = sqlport;
		} else {
			if (this.getSqldatabase()=="mysql"){
				this.sqlport = "3306";
			}
			if (this.getSqldatabase()=="postgresql"){
				this.sqlport = "5432";
			}
		}
	}

	public String getSqltable() {
		return sqltable;
	}

	public void setSqltable(String sqltable) {
		if (sqltable!=null && !sqltable.isEmpty()){
			this.sqltable = sqltable;
		}
	}

	public String getSqluser() {
		return sqluser;
	}

	public void setSqluser(String sqluser) {
		this.sqluser = sqluser;
	}

	public String getSqlpassword() {
		return sqlpassword;
	}

	public void setSqlpassword(String sqlpassword) {
		this.sqlpassword = sqlpassword;
	}
	
	public boolean isUploadHTTP() {
		return uploadHTTP;
	}

	public void setUploadHTTP(boolean uploadHTTP) {
		this.uploadHTTP = uploadHTTP;
	}

	public String getUploadHTTPurl() {
		return uploadHTTPurl;
	}

	public void setUploadHTTPurl(String uploadHTTPurl) {
		this.uploadHTTPurl = uploadHTTPurl;
	}
	
	public boolean checkStatus(Object object){
		if (object.equals("enable") || object.equals("true") || object.equals("1")){
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Measure
	 */

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

	public void setIrtemperature(Float irtemperature) {
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
	
	public Float getCo2() {
		return co2;
	}

	public void setCo2(Float co2) {
		this.co2 = co2;
	}
	
	public boolean checkCompletion() {
		return altitude != null && battery != null && capacitance != null && currentDate != null && humidity != null && irtemperature != null && oxidizinggas != null && precisiongas != null && pressure != null && reducinggas != null && rgb != null && temperature != null;
	}

	/*
	 * Calculations and more
	 */

	public Float getDewpoint() {
		if (getTemperature() != null && getHumidity() != null) {
			return (float) (getTemperature()
					- (14.55 + 0.114 * getTemperature())
					* (1 - (0.01 * getHumidity()))
					- Math.pow(
							((2.5 + 0.007 * getHumidity()) * (1 - (0.01 * getHumidity()))),
							3) - (15.9 + 0.117 * getTemperature())
					* Math.pow((1 - (0.01 * getHumidity())), 14));
		}
		return null;
	}

	// CO2 Sensor / UART
	public float getFloatValueFromUartResponse(byte[] response) {
		String ascii = "";
		int index = 0;
		byte b = response[index];
		// Check for startbyte
		if (b == 0x23) {
			// Reading to end byte or end of byte[]
			while (b != 0x0D && index < response.length) {
				index++;
				b = response[index];
				ascii = ascii + (char) b;
			}
			// endbyte found
			if (b == 0x0D) {
				try {
					// give number back
					//return Integer.parseInt(ascii.trim());
					
					// Note: Sometimes, the CO2 sensor 10 (ReadStatus) returns, if it is not connected to the power! solution!?
					ascii = ascii.trim();
					float uartvalue = Float.parseFloat(ascii);
					if (uartvalue == 10 || ascii == null){ 
						uartvalue = -1;
					}
					return uartvalue;
					
				} catch (Exception e) {
					//System.out.println("UART - Could not parse string to int/float: " + ascii);
					return -1;
				}
			} else {
				//System.out.println("UART - Found no endbyte");
				return -1;
			}
		} else {
			//System.out.println("UART - UART response error / NOT connect to C02 Sensor");
			return -1;
		}
	}
}