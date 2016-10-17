package de.hs_mainz.Sensors.outputs;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.hs_mainz.Sensors.helper.SSLCertificateValidation;
import de.hs_mainz.Sensors.helper.Variables;
import de.hs_mainz.Sensors.interfaces.Output;

public class UploadHTTP implements Output{

	/*
	 * Upload measure via http (php script into Database, when sql no direct connect)
	 */
	
	/*
	 * Help:
	 * 
	 * http://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily
	 * http://www.coderblog.de/sending-data-from-java-to-php-via-a-post-request/
	 * 
	 */
	
	@Override
	public void write(Variables variables) {
		this.uploadMeasure(variables);
	}
	
	private void uploadMeasure(Variables variables) {
		try {
			
			// disable SSL certificate validation in Java!
			SSLCertificateValidation.disable();
			
			// open a connection to the site
			URL url = new URL(variables.getUploadHTTPurl());
			URLConnection con = url.openConnection();
			// activate the output
			con.setDoOutput(true);
			PrintStream ps = new PrintStream(con.getOutputStream());
			// send your parameters to the site
			ps.print("securityKey="+variables.getUploadHTTPsecurityKey());
			ps.print("&id="+variables.getId());
			ps.print("&date="+variables.getDate());
			ps.print("&time="+variables.getTime());
			ps.print("&battery="+variables.getBattery());
			ps.print("&humidity="+variables.getHumidity());
			ps.print("&irtemperature="+variables.getIrtemperature());
			ps.print("&temperature="+variables.getTemperature());
			ps.print("&dewpoint="+variables.getDewpoint());
			ps.print("&pressure="+variables.getPressure());
			ps.print("&lux="+variables.getRgb());
			ps.print("&altitude="+variables.getAltitude());
			ps.print("&capacitance="+variables.getCapacitance());
			ps.print("&oxidizinggas="+variables.getOxidizinggas());
			ps.print("&precisiongas="+variables.getPrecisiongas());
			ps.print("&reducinggas="+variables.getReducinggas());
			ps.print("&co2="+variables.getCo2());
			ps.print("&description="+variables.getDescription());
			
			// get the input stream in order to actually send the request
		    con.getInputStream();
		    
		    // close the print stream
		    ps.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}