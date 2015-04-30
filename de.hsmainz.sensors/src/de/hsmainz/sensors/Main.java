package de.hsmainz.sensors;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Starten der Messung und entsprechen Funktione (je nach Einstellung in config)
		Sensordrone drone = new Sensordrone();
		try {
			drone.execute();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("FAILLL!!!!");
		} catch(Exception e){
			System.out.println("MEGA-FAILLL");
		}
	}

}