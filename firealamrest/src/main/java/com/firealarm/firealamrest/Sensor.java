package com.firealarm.firealamrest;

import javax.xml.bind.annotation.XmlRootElement;

//Class of a sensor
@XmlRootElement
public class Sensor {
	
	private int sensorID;
	private boolean sensorStatus;
	private String sensorLocationFloorNo;
	private String sensorLocationRoomNo;
	private int smokeLevel;
	private int co2Level;
	
	public int getSensorID() {
		return sensorID;
	}
	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}
	public boolean isSensorStatus() {
		return sensorStatus;
	}
	public void setSensorStatus(boolean sensorStatus) {
		this.sensorStatus = sensorStatus;
	}
	public String getSensorLocationFloorNo() {
		return sensorLocationFloorNo;
	}
	public void setSensorLocationFloorNo(String sensorLocationFloorNo) {
		this.sensorLocationFloorNo = sensorLocationFloorNo;
	}
	public String getSensorLocationRoomNo() {
		return sensorLocationRoomNo;
	}
	public void setSensorLocationRoomNo(String sensorLocationRoomNo) {
		this.sensorLocationRoomNo = sensorLocationRoomNo;
	}
	public int getSmokeLevel() {
		return smokeLevel;
	}
	public void setSmokeLevel(int smokeLevel) {
		this.smokeLevel = smokeLevel;
	}
	public int getCo2Level() {
		return co2Level;
	}
	public void setCo2Level(int co2Level) {
		this.co2Level = co2Level;
	}
	@Override
	public String toString() {
		return "Sensor [sensorID=" + sensorID + ", sensorStatus=" + sensorStatus + ", sensorLocationFloorNo="
				+ sensorLocationFloorNo + ", sensorLocationRoomNo=" + sensorLocationRoomNo + ", smokeLevel="
				+ smokeLevel + ", co2Level=" + co2Level + "]";
	}
	
	
}
