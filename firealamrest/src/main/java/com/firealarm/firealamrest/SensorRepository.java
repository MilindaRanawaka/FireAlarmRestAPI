package com.firealarm.firealamrest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SensorRepository {
	
	Connection con = null;
	
	//DB Connection
	public SensorRepository() {
		
		//TODO : Make sure line 19, 20, 21 port Username and PWD same with your computer
		String url = "jdbc:mysql://localhost:3307/fireAlarmAPI";
		String un = "root";
		String pwd = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,un,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//To get all sensor information from DB
	public ArrayList<Sensor> getSensors() {	
		
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		String sql = "select * from sensors";
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				Sensor s = new Sensor();
				
				s.setSensorID(rs.getInt(1));
				s.setSensorStatus(rs.getBoolean(2));
				s.setSensorLocationFloorNo(rs.getString(3));
				s.setSensorLocationRoomNo(rs.getString(4));
				s.setSmokeLevel(rs.getInt(5));
				s.setCo2Level(rs.getInt(6));
				
				sensors.add(s);
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return sensors;	
	}
	
	//To get sensor details of specific sensor details from DB
	public Sensor getSensor(int id) {
		String sql = "select * from sensors where sensorID ="+id;
		Sensor s = new Sensor();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {				
				s.setSensorID(rs.getInt(1));
				s.setSensorStatus(rs.getBoolean(2));
				s.setSensorLocationFloorNo(rs.getString(3));
				s.setSensorLocationRoomNo(rs.getString(4));
				s.setSmokeLevel(rs.getInt(5));
				s.setCo2Level(rs.getInt(6));
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return s;	
	}


	//To add new sensor to DB
	public void createSensor(Sensor s) {
		String sql = "insert into sensors(sensorStatus,sensorLocationFloorNo,sensorLocationRoomNo,smokeLevel,co2Level) values(?,?,?,?,?)";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setBoolean(1, s.isSensorStatus());
			st.setString(2, s.getSensorLocationFloorNo());
			st.setString(3, s.getSensorLocationRoomNo());
			st.setInt(4, s.getSmokeLevel());
			st.setInt(5, s.getCo2Level());
			st.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}


	//To Update smoke and co2 level details of a sensor in DB
	public void updateData(Sensor s) {
		
		String sql = "UPDATE sensors SET smokeLevel=?, co2Level=? WHERE sensorID=?";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, s.getSmokeLevel());
			st.setInt(2, s.getCo2Level());
			st.setInt(3, s.getSensorID());
			
			st.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}		
	}
	
	//To update sensor status sensor floor no and room no in DB
	public void updateSensor(Sensor s) {
		String sql = "UPDATE sensors SET sensorStatus=?, sensorLocationFloorNo=?, sensorLocationRoomNo=? WHERE sensorID=?";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setBoolean(1, s.isSensorStatus());
			st.setString(2, s.getSensorLocationFloorNo());
			st.setString(3, s.getSensorLocationRoomNo());
			st.setInt(4, s.getSensorID());
			
			st.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
