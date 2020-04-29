package com.firealarm.firealamrest;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//This runs in side /webresources
// url url would be http://localhost:8080/firealamrest/webresources/
@Path("sensors")
public class SensorResource {
	
	SensorRepository sensorRepo = new SensorRepository();
	
	//To get all sensors details
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Sensor> getSensors() {
		
		return sensorRepo.getSensors();
	}
	
	//To get specific sensor details
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Sensor getSensor(@PathParam("id") int id) {
		return sensorRepo.getSensor(id);
	}
	
	//To add a sensor
	@POST
	@Path("sensor")
	public Sensor addSensor(Sensor s) {		
		sensorRepo.createSensor(s);
		
		return s;
	}
	
	//To update sensor co2 and smoke level
	@PUT
	@Path("data")
	public Sensor updateSensorData(Sensor s) {
		sensorRepo.updateData(s);
		return s;
	}
	
	//To update sensor status and floor number and room no
	@PUT
	@Path("update")
	public Sensor updateSensor(Sensor s) {
		sensorRepo.updateSensor(s);
		return s;
	}

}
