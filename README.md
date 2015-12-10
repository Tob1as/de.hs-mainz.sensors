Sensors measure
====================

DE: Java Anwendung für die Sensordrone mit vielen Sensoren und CO2-Sensor Modul. 

EN: Java application for Sensordrone with many sensors and CO2-sensor modul.

Functions:
--------------
- Measure with Sensordrone and external CO2 Modul the air
-- Humidity, IR Temperature (in Celsius), Temperature (in Celsius), Pressure, Luminosity, CO2 and more
- Save measure in xml file and/or SQL Database (SQLite, MySQL, PostgreSQL)
- For settings use the generate config.xml-file
- It works on Windows, Linux and Raspberry Pi 2 B (bluecove_arm)

Requirement to use:
--------------
- Bluetooth Adapter [Example: LogiLink BT0015](http://www.logilink.org/showproduct/BT0015.htm?seticlanguage=en) or onboard Bluetooth
- [Sensordrone](http://sensordrone.com/index.php) by Sensorcon
- optional: [CO2-Sensor-Modul](http://www.mb-systemtechnik.de/produkte_co2_messung_co2_sensor_modul_mit_ausgang.htm) via UART

Requirement for Development:
--------------
- IDE Example: [Eclipse](https://eclipse.org/) and [Java](https://www.java.com/)
- [Sensordrone Java Library](http://developer.sensordrone.com/downloads/) -- [sensordrone-java-library-1.3.2-SNAPSHOT](http://developer.sensordrone.com/files/libraries/sensordrone-java-library-1.3.2-SNAPSHOT.jar)
- [BlueCove](http://bluecove.org/), a Java library for Bluetooth: [bluecove-2.1.1-SNAPSHOT](http://snapshot.bluecove.org/distribution/download/2.1.1-SNAPSHOT/2.1.1-SNAPSHOT.63/bluecove-2.1.1-SNAPSHOT.jar) for Windows + [bluecove-gpl-2.1.1-SNAPSHOT](http://snapshot.bluecove.org/distribution/download/2.1.1-SNAPSHOT/2.1.1-SNAPSHOT.63/bluecove-gpl-2.1.1-SNAPSHOT.jar) for Linux (Install on Linux: libbluetooth-dev | [Raspberry Pi](https://www.raspberrypi.org/forums/viewtopic.php?f=81&t=58758))
- JDBC (SQL): [SQLite](https://bitbucket.org/xerial/sqlite-jdbc/downloads/), [MySQL](http://dev.mysql.com/downloads/connector/j/), [PostgreSQL](https://jdbc.postgresql.org/download.html) 

Others:
--------------
- Year: 2015
- de.hsmainz.sensors developed by [Tobias H.](https://github.com/TobiasH87) of the [Hochschule Mainz - University of Applied Sciences Mainz](http://gi.hs-mainz.de/)
