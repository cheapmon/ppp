package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import representation.Texts;

public class TextLoader {
	
	public List<Texts> getTexts(String date_1) throws SQLException{
		DatabaseInitializer db = DatabaseInitializer.getInstance();
		db.connection = null;
		db.initDBConnection();
		
		
		PreparedStatement ps = null;
    	ResultSet rs;
    	List<Texts> allDates = new ArrayList<Texts>();
		try {
			ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM GOOGLE WHERE DATE = ? OR DATE = ?");
	        ps.setString(1, date_1); 
	        ps.setString(2, "January 27, 2009"); 
	        rs = ps.executeQuery();     
	            
			while (rs.next()){
				Texts t = new Texts(rs.getString(1),rs.getString(2));
				allDates.add(t);
				System.out.println(t);

			}

		} catch (SQLException e) {
			System.out.println("Fehler: Datenbankabfrage");
			e.printStackTrace();
		} 
		db.connection.close();
		return allDates;
		
	}
	
	public static void main (String[] args){
		TextLoader tl = new TextLoader();
		try {
			tl.getTexts("June 9, 1999");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
