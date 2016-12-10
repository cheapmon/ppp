package tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import representation.Date;



public class DatabaseInitializer {
	
	public static final DatabaseInitializer dbcontroller = new DatabaseInitializer(); 
    public Connection connection; 
    private static final String DB_PATH = "C:\\Users\\Alexander\\Desktop\\Main\\Entwicklung\\tmcrawl" + "/" + "tmcrawl.db"; 
	
    static { 
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) { 
            System.err.println("Fehler beim Laden des JDBC-Treibers"); 
            e.printStackTrace(); 
        } 
    } 
    
    public DatabaseInitializer(){ 
    } 
     
    public static DatabaseInitializer getInstance(){ 
        return dbcontroller; 
    }
    
    public void initDBConnection() { 
    	
        try { 
            if (connection != null) 
                return; 
            System.out.println("Creating Connection to Database..."); 
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); 
            if (!connection.isClosed()) 
                System.out.println("...Connection established"); 
        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        } 

        Runtime.getRuntime().addShutdownHook(new Thread() { 
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
    
    public void handleDB() { 
    	
    	ResultSet rs = null;
    	Statement stmt = null;
    	List<String> res = new ArrayList<String>();
        try { 
        	stmt = connection.createStatement();
        	
        	rs = stmt.executeQuery("SELECT DATE FROM GOOGLE");
        	//System.out.println(rs);
            while (rs.next()){
            	res.add(rs.getString("DATE"));
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
    

    
    
	public static void main (String[] args ){
		DatabaseInitializer dbc = DatabaseInitializer.getInstance(); 
       
        
        
	}
	
	
}