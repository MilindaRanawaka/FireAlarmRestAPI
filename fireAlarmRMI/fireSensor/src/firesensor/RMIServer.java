package firesensor;

import Models.Sensor;
import Models.User;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

public class RMIServer extends UnicastRemoteObject implements RMIServiceInterface {

    private List<User> userList;
    private List<Sensor> sensorList;
    private Connection con = null;
    
    private static HttpURLConnection conn;

    public RMIServer() throws RemoteException {
        super();
        System.out.println("Server start");
        initializeData();
    }

    public static void main(String[] args) {
        try {
            
            Registry reg = LocateRegistry.createRegistry(9999);
            reg.rebind("rmi_server", new RMIServer());
            System.err.println("Server ready");
            
        } catch (Exception e) {
            
            System.err.println("Server exception: " + e.getMessage());
            
        }
    }

    private void initializeData() {

        userList = new ArrayList<>();
        sensorList = new ArrayList<>();

        serverUpdate();

    }
    
    public void addSensorData(Sensor s) {
        
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        
        try {
            
            URL url = new URL("http://localhost:8080/firealamrest/webresources/sensors/sensor");
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = "{\"co2Level\": \"0\",\"sensorID\": \""+s.getSensorID()+"\",\"sensorLocationFloorNo\": \""+s.getSensorLocationFloorNo()+"\",\"sensorLocationRoomNo\": \""+s.getSensorLocationRoomNo()+"\",\"sensorStatus\": \""+s.getSensorStatus()+"\",\"smokeLevel\": \"0\"}";
        
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
    
    public void updateSensorData(Sensor s) {
        
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        
        try {
          URL url = new URL("http://localhost:8080/firealamrest/webresources/sensors/update");
          conn = (HttpURLConnection) url.openConnection();

          conn.setRequestMethod("PUT");
          conn.setRequestProperty("Content-Type", "application/json");
          conn.setDoOutput(true);
          String jsonInputString = "{\"sensorID\": \""+s.getSensorID()+"\",\"sensorLocationFloorNo\": \""+s.getSensorLocationFloorNo()+"\",\"sensorLocationRoomNo\": \""+s.getSensorLocationRoomNo()+"\",\"sensorStatus\": \""+s.getSensorStatus()+"\"}";
          
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
    
    @Override
    public User searchUser(User user) throws RemoteException {
        
        Predicate<User> predicate = x -> x.getUserID() == user.getUserID();
        
        return userList.stream().filter(predicate).findFirst().get();
        
    }

    @Override
    public List<User> allUser() throws RemoteException {
        
        return userList;
        
    }

    @Override
    public Sensor searchSensor(Sensor s) throws RemoteException {
        
        Predicate<Sensor> predicate = x -> x.getSensorID() == s.getSensorID();
        
        return sensorList.stream().filter(predicate).findFirst().get();
        
    }

    @Override
    public List<Sensor> allSensors() throws RemoteException {
        
        return sensorList;
    }

    private void serverUpdate() {
        System.out.println("Server Update Function Execute");
        
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
       
        String sql = "select * from users";
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setContactno(rs.getString(5));
                
                userList.add(user);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("Server Thread Start");
                    apiRequests();
                    System.out.println("15sec Delay");
                    Thread.sleep(15000);
                    serverUpdate();
                } catch (Exception e) {
                    System.out.println("Thread Exception : " + e);
                }

            }
        }
        );

        thread.start();

    }
   
    private String jsonRequest(String url) {
        String response = null;

        try {
            URL u = new URL(url);
            HttpURLConnection hr = (HttpURLConnection) u.openConnection();
            if (hr.getResponseCode() == 200) {
                InputStream im = hr.getInputStream();
                StringBuffer sb = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(im));
                String line = br.readLine();
                response = line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    
    private void apiRequests() {
        System.out.println("Send API Requests");

        String sensor_response = jsonRequest("http://localhost:8080/firealamrest/webresources/sensors");
        System.out.println("sensor_response"+sensor_response);
        
        if (sensor_response != null) {
            try {
                
                JSONParser jsonParser = new JSONParser();
                
                JSONObject responseObj = (JSONObject) jsonParser.parse(sensor_response);                
                
                JSONArray array = (JSONArray) responseObj.get("sensor");                            
                             
                sensorList.clear();
                
                for (Object obj : array) {
                    JSONObject jSONObject = (JSONObject) obj;                 
                    sensorList.add(
                            new Sensor(
                                    Integer.parseInt(jSONObject.get("sensorID").toString()),
                                    Integer.parseInt(jSONObject.get("co2Level").toString()),
                                    (jSONObject.get("sensorLocationFloorNo") == null) ? "" : jSONObject.get("sensorLocationFloorNo").toString(),
                                    (jSONObject.get("sensorLocationRoomNo") == null) ? "" : jSONObject.get("sensorLocationRoomNo").toString(),
                                    Boolean.parseBoolean(jSONObject.get("sensorStatus").toString()),
                                    Integer.parseInt(jSONObject.get("smokeLevel").toString()))                                                                     
                   );
                }
                                
                System.out.println("Response from " + sensorList.size() + " sensors");
                
            } catch (Exception e) {
                System.out.println("Server Exception: " + e);
            }
        }
               
    }

}
