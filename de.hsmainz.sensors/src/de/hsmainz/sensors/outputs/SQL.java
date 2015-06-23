package de.hsmainz.sensors.outputs;

import java.sql.*;

import de.hsmainz.sensors.helper.Variables;
import de.hsmainz.sensors.interfaces.Output;

public class SQL implements Output {

	/*
	 * Save Measurements in SQL
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
	
	private String datatype= "DOUBLE";
	
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
			if (variables.getSqldatabasetype().equalsIgnoreCase("sqlite")) {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:"+variables.getFilenameSQLite());
			}
			if (variables.getSqldatabasetype().equalsIgnoreCase("mysql")) {
				Class.forName("com.mysql.jdbc.Driver");
				c = DriverManager.getConnection("jdbc:mysql://"+ variables.getSqlhost() +":"+ variables.getSqlport() +"/"+ variables.getSqldatabasename(),variables.getSqluser(),variables.getSqlpassword());
			}
			if (variables.getSqldatabasetype().equalsIgnoreCase("postgresql")) {
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://"+ variables.getSqlhost() +":"+ variables.getSqlport() +"/"+ variables.getSqldatabasename(),variables.getSqluser(),variables.getSqlpassword());
				datatype= "DOUBLE PRECISION";
			}

			stmt = c.createStatement();
			
			// Create Table
			String sqlcreate = "CREATE TABLE IF NOT EXISTS "+ variables.getSqltable() +" " +
					"(id "+ datatype +" PRIMARY KEY  NOT NULL," +
					" date           TEXT    NOT NULL, " + 
					" time           TEXT    NOT NULL, " + 
					" battery        "+ datatype +"  NOT NULL, " + 
					" humidity       "+ datatype +"  NOT NULL, " + 
					" irtemperature  "+ datatype +"  NOT NULL, " + 
					" temperature    "+ datatype +"  NOT NULL, " + 
					" dewpoint       "+ datatype +"  NOT NULL, " + 
					" pressure       "+ datatype +"  NOT NULL, " + 
					" lux            "+ datatype +"  NOT NULL, " + 
					" altitude       "+ datatype +"  NOT NULL, " + 
					" capacitance    "+ datatype +"  NOT NULL, " + 
					" oxidizinggas   "+ datatype +"  NOT NULL, " + 
					" precisiongas   "+ datatype +"  NOT NULL, " + 
					" reducinggas    "+ datatype +"  NOT NULL, " + 
					" co2            "+ datatype +"  NOT NULL, " +
					" description    TEXT    NOT NULL) "; 	      
			stmt.executeUpdate(sqlcreate);
			// Insert Values
			String sqlinsert = "INSERT INTO "+ variables.getSqltable() +" "
					+ "(id, date, time, battery, humidity, irtemperature, temperature, dewpoint, pressure, lux, altitude, capacitance, oxidizinggas, precisiongas, reducinggas, co2, description) " + "VALUES"
					+ "("+ variables.getId() + ",\'" + variables.getDate() + "\',\'"+ variables.getTime() + "\'," + variables.getBattery() + "," + variables.getHumidity() + "," + variables.getIrtemperature() + "," + variables.getTemperature() + "," + variables.getDewpoint() + ","+ variables.getPressure() + ","+ variables.getRgb() + ","+ variables.getAltitude() + "," + variables.getCapacitance() + "," + variables.getOxidizinggas() + "," + variables.getPrecisiongas() + "," + variables.getReducinggas() + "," + variables.getCo2() + ",\'" + variables.getDescription() + "\')";
			stmt.executeUpdate(sqlinsert);
			
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			//System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}	
}