import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class MainClass {
	private static HttpURLConnection conn;
	private static Connection sqlConn;
	
	public static ArrayList<String> getInfo() {
		
		String url = "jdbc:mysql://localhost:3307/fireAlarmAPI";
		String un = "root";
		String pwd = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			sqlConn = DriverManager.getConnection(url,un,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> strArr = new ArrayList<String>();
		String sql = "select email,contactno from users";
		String email = "";
		String contactNo = "";
		try {
			Statement st = sqlConn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				email = rs.getString(1);
				contactNo = rs.getString(2);
				strArr.add(email);
				strArr.add(contactNo);
			}
			sqlConn.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return strArr;	
	}
	
	public static void sendMail(String sendMail,String floor, String room) {
		final String username = "milindaranawaka2@gmail.com";
        final String password = "660_don't_forget_your_password_3675";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("milindaranawaka2@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(sendMail)
            );
            message.setSubject("Fire Alarm Warning");
            String bodyText="Fire Sensor triggered!\nFloor no: "+floor+" in Room no: "+room+"";
            message.setText(bodyText);

            Transport.send(message);

            System.out.println("Email sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
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
			
			JSONArray sensors = new JSONArray(responseContent.toString().substring(10,responseContent.toString().substring(1).length()));
			
			for(int i=0; i<sensors.length(); i++) {
				JSONObject sensor = sensors.getJSONObject(i);
				int id = sensor.getInt("sensorID");
				int co2 = sensor.getInt("co2Level");
				int smoke = sensor.getInt("smokeLevel");
				String floor = sensor.getString("sensorLocationFloorNo");
				String room = sensor.getString("sensorLocationRoomNo");
				
				if((smoke > 5)||(co2 > 5)) {
					sendMail(getInfo().get(0), floor, room);
					System.out.println("SMS Send to : "+getInfo().get(1)+" Number");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		while(true) {
			getList();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

}