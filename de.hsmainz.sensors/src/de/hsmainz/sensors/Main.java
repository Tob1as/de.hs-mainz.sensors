package de.hsmainz.sensors;

import java.util.ArrayList;
import java.util.List;

import de.hsmainz.sensors.helper.CLI;
import de.hsmainz.sensors.helper.Output;
import de.hsmainz.sensors.helper.SQL;
import de.hsmainz.sensors.helper.Variables;
import de.hsmainz.sensors.helper.XML;

public class Main {

	/* TODO Allgemein!
	 * 1. Erstelle config mit Beispieldaten, wenn nicht existiert 
	 * 1.1.1 http://www.mkyong.com/java/java-properties-file-examples/
	 * 1.1.2 http://de.wikipedia.org/wiki/Java-Properties-Datei
	 * 2. Lade config mit settings zu pfaden, speicher SQL/XML true/false, Datenbanksettings usw.
	 * 3. Erstelle Datenbank, falls Sie nicht existiert. (Settings aus config)
	 * 3.1 Schreibe Werte in Datenbank (optional: wahl zwischen MySQL, PostgreSQL und SQLite)
	 * 4. Lese Daten via php script aus (vorzugsweise aus Datenbank, optional mit generierten Grafik)!
	 * 5. optional: XML: fortführen und speichern weiterer Werte statt immer nur den letzten!
	 * 6. optional: CO2 Sensor integieren
	 * 7. optional: Testbetrieb ermöglichen und Werte generieren, falls Sensordrone nicht verf�gbar!
	 */
	
	public static void main(String[] args) {
		
		Variables variables = new Variables();
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

		if (variables.checkCompletion()) {
			for (Output output : lst) {
				output.write(variables);
			}
		}
	}

}