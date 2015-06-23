package de.hsmainz.sensors;

import java.util.ArrayList;
import java.util.List;

import de.hsmainz.sensors.helper.Config;
import de.hsmainz.sensors.helper.Variables;
import de.hsmainz.sensors.interfaces.ISensor;
import de.hsmainz.sensors.interfaces.Output;
import de.hsmainz.sensors.outputs.CLI;
import de.hsmainz.sensors.outputs.SQL;
import de.hsmainz.sensors.outputs.UploadHTTP;
import de.hsmainz.sensors.outputs.XML;

public class Main {

	/* 
	 * Sensors Main
	 */
	
	public static void main(String[] args) {
		
		Variables variables = new Variables();
		
		Config conf = new Config();
		conf.config(variables);
		
		List<ISensor> lst = new ArrayList<ISensor>();
		if (variables.isMeasuredSensorDrone()==true){lst.add(new Sensordrone(variables));}
		
		try {
			for (ISensor sensor : lst) {
				sensor.execute();
			}
			output(variables);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//System.out.println(e);
			//e.getMessage();
			e.printStackTrace();
		}
	}

	public static void output(Variables variables) {

		List<Output> lst = new ArrayList<Output>();
		if (variables.isShowCLI()==true){lst.add(new CLI());}
		if (variables.isWriteXML()==true){lst.add(new XML());}
		if (variables.isSaveSQL()==true){lst.add(new SQL());}
		if (variables.isUploadHTTP()==true){lst.add(new UploadHTTP());}

		if (variables.checkCompletion()) {
			for (Output output : lst) {
				output.write(variables);
			}
		}
	}

}