package de.hs_mainz.Sensors;

import java.util.ArrayList;
import java.util.List;

import de.hs_mainz.Sensors.helper.Config;
import de.hs_mainz.Sensors.helper.Variables;
import de.hs_mainz.Sensors.interfaces.ISensor;
import de.hs_mainz.Sensors.interfaces.Output;
import de.hs_mainz.Sensors.outputs.CLI;
import de.hs_mainz.Sensors.outputs.SQL;
import de.hs_mainz.Sensors.outputs.UploadHTTP;
import de.hs_mainz.Sensors.outputs.XML;

public class Main {

	/* 
	 * Sensors Main
	 */
	
	public static void main(String[] args) {
		
		Variables variables = new Variables();
		
		Config conf = new Config();
		conf.config(variables);
		
		// Sensors list
		List<ISensor> lst = new ArrayList<ISensor>();
		if (variables.isMeasuredSensorDrone()==true){lst.add(new Sensordrone(variables));}
		
		// execute sensors and write measure
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
		
		// Output list
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