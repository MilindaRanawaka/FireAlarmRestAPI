package com.firealarm.firealamrest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ArrayList;

public class SensorRepository {

	ArrayList<Sensor> sensors;
	
	public SensorRepository() {
		sensors = new ArrayList<Sensor>();
		
		Sensor s1 = new Sensor();
		
		s1.setSensorID(101);
		s1.setSensorStatus(true);
		s1.setSensorLocationFloorNo("F2");
		s1.setSensorLocationRoomNo("N202");
		s1.setSmokeLevel(3);
		s1.setCo2Level(2);
		
		Sensor s2 = new Sensor();
		
		s2.setSensorID(102);
		s2.setSensorStatus(true);
		s2.setSensorLocationFloorNo("F1");
		s2.setSensorLocationRoomNo("N102");
		s2.setSmokeLevel(1);
		s2.setCo2Level(1);
		
		sensors = new ArrayList<Sensor>(); 
		
		sensors.add(s1);
		sensors.add(s2);
	}
	
	
	public ArrayList<Sensor> getSensors() {	
		return sensors;	
	}
	
	public Sensor getSensor(int id) {
		for(Sensor s : sensors) {
			if(s.getSensorID()==id) {
				return s;
			}
		}
		
		return null;
	}


	public void createSensor(Sensor s) {
		sensors.add(s);
	}
}
