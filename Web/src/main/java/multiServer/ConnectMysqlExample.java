package multiServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class ConnectMysqlExample {
    private static String DB_URL = "jdbc:mysql://localhost:3306/iot_course";
    private static String USER_NAME = "root";
    private static String PASSWORD = "hieutt";
 
    /**

     * @author viettuts.vn
     * @param args
     */
    public static void main(String args[]) {
        try {
            // connnect to database 'testdb'
            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            
//            stmt.executeUpdate("INSERT INTO student(id, name, address) "
//                    + "VALUES (5, 'Vinh', 'Hanoi')");
//                   
//            ResultSet rs = stmt.executeQuery("select * from student");
//            // show data
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + "  " + rs.getString(2) 
//                        + "  " + rs.getString(3));
//            }
            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void insertDB(String name, String address, String protocol, String value) {
    	try {
            // connnect to database 'testdb'
            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            
            String val= "VALUES (" + 0 + ",'" + name + "','" + address + "','" + protocol + "','" + value + "')";
            
            System.out.println("> JDBC: SQL: " + "INSERT INTO temperature(id, name, address, protocol, temperature)" + val );
            stmt.executeUpdate("INSERT INTO temperature(id, name, address, protocol, temperature)"
                    + val);
                   
//            ResultSet rs = stmt.executeQuery("select * from student");
//            // show data
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + "  " + rs.getString(2) 
//                        + "  " + rs.getString(3));
//            }
            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	
    }
    
    public String gettDB(String id) {
    	String rt = "";
    	try {
            // connnect to database 'testdb'
            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            String Querry = "select * from temperature";
            String jsonQuerry = "SELECT JSON_ARRAYAGG(JSON_OBJECT('id', id,'name', name, 'address', address,'protocol', protocol, 'temperature', temperature)) from temperature";
            ResultSet rs = stmt.executeQuery(Querry);
            // show data
           
      //     System.out.println(rs.getString(1));
            
          //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            
            while (rs.next()) {
           
            	String row = rs.getString(1) + "  " + rs.getString(2) 
                + "  " + rs.getString(3) + "  " + rs.getString(4) 
                + "  " + rs.getString(5) + "\n";
                System.out.print(row);
                rt+=row;
                
                
                JSONObject record = new JSONObject();
                //Inserting key-value pairs into the json object
                record.put("id", rs.getInt("id"));
                record.put("name", rs.getString("name"));
                record.put("address", rs.getString("address"));
                record.put("protocol", rs.getString("protocol"));
                record.put("temperature", rs.getString("temperature"));
                array.add(record);
                
               
            }
            
            rt = array.toJSONString();
            System.out.println(array.toJSONString());
           //  close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return rt;
    	
    }
 
 
    /**
     * create connection 
     * 
     * @author viettuts.vn
     * @param dbURL: database's url
     * @param userName: username is used to login
     * @param password: password is used to login
     * @return connection
     */
    public static Connection getConnection(String dbURL, String userName, 
            String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("> JDBC: connect to MySQL successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}