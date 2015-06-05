package de.hsmainz.sensors.helper;

import java.io.File;
import java.sql.*;

public class SQL implements Output {

	/*
	 * Write Measurements in XML
	 */
	
	/*
	 * Help:
	 * 
	 * http://www.mkyong.com/tutorials/jdbc-tutorials/
	 * http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm AND http://www.tutorialspoint.com/postgresql/postgresql_java.htm AND http://www.tutorialspoint.com/sqlite/sqlite_java.htm
	 * http://de.wikibooks.org/wiki/Java_Standard:_JDBC
	 * 
	 * https://bitbucket.org/xerial/sqlite-jdbc/downloads
	 * 
	 */
	
	@Override
	public void write(Variables variables) {
		if (variables.isSaveSQL()==true && variables.getSqldatabase()== "sqlite") {
			this.saveMeasureInSQLite(variables);
		}
		if (variables.isSaveSQL()==true && variables.getSqldatabase()== "mysql") {
			this.saveMeasureInMySQL(variables);
		}
		if (variables.isSaveSQL()==true && variables.getSqldatabase()== "postgresql") {
			this.saveMeasureInPostgreSQL(variables);
		}
	}
	
	private void saveMeasureInSQLite(Variables variables) {
		
		// TODO SQLite
		
		File sqlfile = new File ("sensors.sqlite");
		
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+sqlfile);
			stmt = c.createStatement();
			
			// Create Table
			String sqlcreate = "CREATE TABLE IF NOT EXISTS measure " +
					"(ID INT PRIMARY KEY      NOT NULL," +
					" date           TEXT     NOT NULL, " + 
					" time           TEXT     NOT NULL, " + 
					" battery        NUMERIC  NOT NULL, " + 
					" humidity       NUMERIC  NOT NULL, " + 
					" irtemperature  NUMERIC  NOT NULL, " + 
					" temperature    NUMERIC  NOT NULL, " + 
					" dewpoint       NUMERIC  NOT NULL, " + 
					" pressure       NUMERIC  NOT NULL, " + 
					" lux            NUMERIC  NOT NULL, " + 
					" altitude       NUMERIC  NOT NULL, " + 
					" capacitance    NUMERIC  NOT NULL, " + 
					" oxidizinggas   NUMERIC  NOT NULL, " + 
					" precisiongas   NUMERIC  NOT NULL, " + 
					" reducinggas    NUMERIC  NOT NULL, " + 
					" co2            NUMERIC  NOT NULL)"; 	      
			stmt.executeUpdate(sqlcreate);
			// Insert Values
			String sqlinsert = "INSERT INTO measure"
					+ "(ID, date, time, battery, humidity, irtemperature, temperature, dewpoint, pressure, lux, altitude, capacitance, oxidizinggas, precisiongas, reducinggas, co2) " + "VALUES"
					+ "("+ variables.getId() + ",\'" + variables.getDate() + "\',\'"+ variables.getTime() + "\',"+ variables.getBattery() + ","+ variables.getHumidity() + ","+ variables.getIrtemperature() + ","+ variables.getTemperature() + ","+ variables.getDewpoint() + ","+ variables.getPressure() + ","+ variables.getRgb() + ","+ variables.getAltitude() + ","+ variables.getCapacitance() + ","+ variables.getOxidizinggas() + ","+ variables.getPrecisiongas() + ","+ variables.getReducinggas() + ","+ variables.getCo2() + ")";
			stmt.executeUpdate(sqlinsert);
			
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	private void saveMeasureInMySQL(Variables variables) {
		
		// TODO MySQL
		
	}
	
	private void saveMeasureInPostgreSQL(Variables variables) {
		
		// TODO PostgreSQL
		
	}
	
}