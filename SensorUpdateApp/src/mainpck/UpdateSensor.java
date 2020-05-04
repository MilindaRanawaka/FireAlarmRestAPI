package mainpck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

//Update Sensor data
public class UpdateSensor {
	private static HttpURLConnection conn;
	
	//Generate Random Number
	public static int getRandom() {
		Random random = new Random();
		int rand = 0;
		while (true){
		    rand = random.nextInt(101);
		    if(rand !=0) break;
		}
		
		//Generate Random number with probability
		if (rand <= 1) {
			return 10;
		} else if (rand <= 3) {
			return 9;
		} else if (rand <= 7) {
			return 8;
		} else if (rand <= 13) {
			return 7;
		} else if (rand <= 20) {
			return 6;
		} else if (rand <= 28) {
			return 5;
		} else if (rand <= 38) {
			return 4;
		} else if (rand <= 54) {
			return 3;
		} else if (rand <= 75) {
			return 2;
		}
		else {
			return 1;
		}
	}
	
	//To get all Sensor List
	public static void getList() {
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try{
			URL url = new URL("http://localhost:8080/firealamrest/webresources/sensors");
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			
			int status = conn.getResponseCode();
			System.out.println(status);
			
			if (status>299) {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			System.out.println(responseContent.toString().substring(10,responseContent.toString().substring(1).length()));
			
			JSONArray sensors = new JSONArray(responseContent.toString().substring(10,responseContent.toString().substring(1).length()));
			
			for(int i=0; i<sensors.length(); i++) {
				JSONObject sensor = sensors.getJSONObject(i);
				int id = sensor.getInt("sensorID");
				boolean sensorStatus = sensor.getBoolean("sensorStatus");
				System.out.println(id);
				System.out.println(sensorStatus);
				if(sensorStatus) {
					replaceSensorData(id);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//This method will update Sensor data (co2 level and smoke level)
	public static void replaceSensorData(int sensorID) {
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		int co2 = getRandom();
		int smoke = getRandom();
		try {
			URL url = new URL("http://localhost:8080/firealamrest/webresources/sensors/data");
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			String jsonInputString = "{\"sensorID\": \""+sensorID+"\",\"co2Level\": \""+co2+"\",\"smokeLevel\": \""+smoke+"\"}";
			try(OutputStream os = conn.getOutputStream()) {
			    byte[] input = jsonInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);           
			}
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			
			int status = conn.getResponseCode();
			System.out.println(status);
			
			if (status>299) {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
	}
	
	
	public static void main(String[] args) {
		
		//Calling getList every 10 second
		while(true) {
			getList();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}