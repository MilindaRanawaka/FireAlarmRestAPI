package com.firealarm.firealamrest;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sensors")
public class SensorResource {
	
	SensorRepository sensorRepo = new SensorRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Sensor> getSensors() {
		
		return sensorRepo.getSensors();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Sensor getSensor(@PathParam("id") int id) {
		return sensorRepo.getSensor(id);
	}
	
	@POST
	@Path("sensor")
	public Sensor addSensor(Sensor s) {
		
		System.out.println(s);
		sensorRepo.createSensor(s);
		
		return s;
	}

}
