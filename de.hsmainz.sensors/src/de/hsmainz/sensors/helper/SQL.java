package de.hsmainz.sensors.helper;

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
		if (variables.isSaveSQL()==true){
			this.saveMeasureInSQL(variables);
		}
	}
	
	private void saveMeasureInSQL(Variables variables) {
		Connection c = null;
		Statement stmt = null;
		
		try {
			if (variables.getSqldatabase()=="sqlite") {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:"+variables.getFilenameSQLite());
			}
			if (variables.getSqldatabase()=="mysql") {
				Class.forName("com.mysql.jdbc.Driver");
				c = DriverManager.getConnection("jdbc:mysql://"+ variables.getSqlhost() +":"+ variables.getSqlport() +"/"+ variables.getSqltable(),variables.getSqluser(),variables.getSqlpassword());
			}
			if (variables.getSqldatabase()=="postgresql") {
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://"+ variables.getSqlhost() +":"+ variables.getSqlport() +"/"+ variables.getSqltable(),variables.getSqluser(),variables.getSqlpassword());
			}

			stmt = c.createStatement();
			
			// Create Table
			String sqlcreate = "CREATE TABLE IF NOT EXISTS "+ variables.getSqltable() +" " +
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
			String sqlinsert = "INSERT INTO "+ variables.getSqltable() +" "
					+ "(ID, date, time, battery, humidity, irtemperature, temperature, dewpoint, pressure, lux, altitude, capacitance, oxidizinggas, precisiongas, reducinggas, co2) " + "VALUES"
					+ "("+ variables.getId() + ",\'" + variables.getDate() + "\',\'"+ variables.getTime() + "\',"+ variables.getBattery() + ","+ variables.getHumidity() + ","+ variables.getIrtemperature() + ","+ variables.getTemperature() + ","+ variables.getDewpoint() + ","+ variables.getPressure() + ","+ variables.getRgb() + ","+ variables.getAltitude() + ","+ variables.getCapacitance() + ","+ variables.getOxidizinggas() + ","+ variables.getPrecisiongas() + ","+ variables.getReducinggas() + ","+ variables.getCo2() + ")";
			stmt.executeUpdate(sqlinsert);
			
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}	
}