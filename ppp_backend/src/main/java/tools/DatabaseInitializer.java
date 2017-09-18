package tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * initialize the SQLite database
 *
 */
public class DatabaseInitializer {
	
	/**
	 * new DatabaseInitializer
	 */
	public static final DatabaseInitializer dbcontroller = new DatabaseInitializer(); 
    
	/**
     * new Connection
     */
	public Connection connection; 
    
	/**
     * path to database
     */
    private static final String DB_PATH = System.getProperty("user.home") + "/Desktop/Main/Entwicklung/ppp/policies.db";
    
    /**
     * load new JDBC driver 
     */
    static { 
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) { 
            System.err.println("Fehler beim Laden des JDBC-Treibers"); 
            e.printStackTrace(); 
        } 
    } 
    
    /**
     * constructor for DatabaseInitializer
     */
    public DatabaseInitializer(){ 
    } 
    
    /**
     * method to get an instance of DatabaseInitializer
     * @return dbcontroller: instance for DatabaseInitializer
     */
    public static DatabaseInitializer getInstance(){ 
        return dbcontroller; 
    }
    
    /**
     * initialize db connection
     */
    public void initDBConnection() {
    	//try to connect with given driver and path
        try { 
            if (connection != null) 
                return; 
            System.out.println("Creating Connection to Database...");
            System.out.println("jdbc:sqlite:" + DB_PATH);
            try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            }catch (Exception e) {
                System.out.println(e);}
            if (!connection.isClosed()) 
                System.out.println("...Connection established"); 
        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        } 
        //recognize abort signal and release resources
        Runtime.getRuntime().addShutdownHook(new Thread() {
        	/**
        	 * recognize abort signal and release resources
        	 */
            public void run() { 
                try { 
                    if (!connection.isClosed() && connection != null) { 
                        connection.close(); 
                        if (connection.isClosed()) 
                            System.out.println("Connection to Database closed"); 
                    } 
                } catch (SQLException e) { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
    }
    
    /**
     * example method to create a resultset and statement
     */
    public void handleDB() {    	
    	ResultSet rs = null;
    	Statement stmt = null;
    	List<String> res = new ArrayList<String>();
        try { 
        	stmt = connection.createStatement();
        	
        	rs = stmt.executeQuery("SELECT SYSTEM_DATE FROM GOOGLE");
        	//System.out.println(rs);
            while (rs.next()){
            	res.add(rs.getString("SYSTEM_DATE"));
            }             	
        } catch (SQLException e) { 
            System.err.println("Couldn't handle DB-Query"); 
            e.printStackTrace(); 
        } catch (Exception e) {
        	System.err.println("Convert to JSON failed");
			e.printStackTrace();
		}
        System.out.print(res);
    }    

    /**
     * main method
     * @param args
     */
	public static void main (String[] args ){
		DatabaseInitializer dbc = DatabaseInitializer.getInstance();  
	}
}
