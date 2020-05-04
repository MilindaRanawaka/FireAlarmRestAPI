package Models;

import java.io.Serializable;

public class Sensor implements Serializable {

    public int sensorID;
    public int co2Level;
    public String sensorLocationFloorNo;
    public String sensorLocationRoomNo;
    public Boolean sensorStatus;
    public int smokeLevel;
    

    public Sensor() {
    }
    
    public Sensor(int sensorID, int co2Level, String sensorLocationFloorNo, String sensorLocationRoomNo, Boolean sensorStatus, int smokeLevel) {
        this.sensorID = sensorID;
        this.co2Level = co2Level;
        this.sensorLocationFloorNo = sensorLocationFloorNo;
        this.sensorLocationRoomNo = sensorLocationRoomNo;
        this.sensorStatus = sensorStatus;
        this.smokeLevel = smokeLevel;
    }


    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }
    
    public int getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
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

    public Boolean getSensorStatus() {
        return sensorStatus;
    }

    public void setSensorStatus(Boolean sensorStatus) {
        this.sensorStatus = sensorStatus;
    }

    public int getSmokeLevel() {
        return smokeLevel;
    }

    public void setSmokeLevel(int smokeLevel) {
        this.smokeLevel = smokeLevel;
    }

    

    

}
