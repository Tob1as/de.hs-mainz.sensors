package de.hsmainz.sensors;

import java.io.UnsupportedEncodingException;

import com.sensorcon.sensordrone.dev.java.Drone;

public class CO2 {
	
	/*
	 * TODO CO2 Sensor wieder in Betrieb nehmen und in Sensordrone.java integieren
	 */
	
	
	
	
	/*
	 * byte to hex
	 * Source: http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
	 */
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 3];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 3] = hexArray[v >>> 4];
			hexChars[j * 3 + 1] = hexArray[v & 0x0F];
			// add spaces
			hexChars[j * 3 + 2] = 0x20;
		}
		return new String(hexChars);
	}
	
	/*
	 * Main -> Connection to the drone and read the CO2 values ​​(ppm) via the UART
	 */
	public static void measureCO2() {
		
		Drone myDrone = new Drone();
		
		// Check the connection
		if (myDrone.isConnected) {
			System.out.println("CONNECTED!");
		} else {
			while (!myDrone.btConnect("0017ec11c070")) {
				System.out.println("waiting");
			}
		}
		
		// Set baudrate
		//myDrone.setBaudRate_38400();
		System.out.println("Setting baudrate to 38400: "+myDrone.setBaudRate_38400());
		
		// Command
		byte[] getStatusCommand = { 0x23, 0x31, 0x30, 0x0D };		// 10 Read status
		
		// Set LED color before UART Write/Read
		myDrone.setLEDs(100, 100, 100);
		
		// UART Write/Read
		byte[] response = myDrone.uartWriteForRead(getStatusCommand, 5);
		
		// Set LED color after UART Write/Read
		myDrone.setLEDs(0, 100, 0);
		
		// Show Array as String (disable)
		//System.out.println(Arrays.toString(response) + " ArrayToString!");
		
		// Show bytes in hex values (optinal)
		System.out.println(bytesToHex(response) + " Hex-Werte");
		
		// Save bytes in ASCII String and show as ....
		/*
		try {
			String ascii = new String(response, "UTF-8");
			// ... ASCII String
			//System.out.println(ascii.substring(1, 5)  + "ppm (CO2-Wert)");
			// ... Double
			//System.out.println(Double.parseDouble(ascii.substring(1, 5))  + "ppm (CO2-Wert)");
			// ... INT
			System.out.println(Integer.parseInt(ascii.substring(1, 5))  + "ppm (CO2-Wert)");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		
		System.out.println(getIntValueFromUartResponse(response));
		
		// closes the connection
		myDrone.disconnect();
	}
	
	// CO2 Sensor / UART
	private static int getIntValueFromUartResponse(byte[] response) {
		String ascii = "";
		int index = 0;
		byte b = response[index];
		// Prüfe auf startbyte
		if (b == 0x23) {
			// lese bis endbyte oder ende von byte[]
			while (b != 0x0D && index < response.length) {
				index++;
				b = response[index];
				ascii = ascii + (char) b;
			}
			// endbyte gefunden
			if (b == 0x0D) {
				try {
					// gebe zahl zurück
					return Integer.parseInt(ascii.trim());
				} catch (Exception e) {
					System.out.println("CO2 - Konnte String nicht zu int parsen: " + ascii);
					return -1;
				}
			} else {
				System.out.println("CO2 - Found no endbyte");
				return -1;
			}
		} else {
			System.out.println("CO2 - UART response error / NOT connect to C02 Sensor");
			return -1;
		}
		// Notiz: Gibt manchmal 10 (Read status) aus, wenn CO2 Sensor nicht mit Strom verbunden.... evt. auch als -1 zählen?? oder anderst lösen
	}
}