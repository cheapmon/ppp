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
	
	public List<Texts> getTexts(String company, String date) throws SQLException{
		DatabaseInitializer db = DatabaseInitializer.getInstance();
		db.connection = null;
		db.initDBConnection();
		
		
		PreparedStatement ps = null;
    	ResultSet rs;
    	List<Texts> allDates = new ArrayList<Texts>();
    	
   		try {
								
				switch (company){
					case "google":
						System.out.println("Hello");
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM GOOGLE WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "whatsapp":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM WHATSAPP WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "wikimedia":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM WIKIMEDIA WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "twitter":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM TWITTER WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "edeka":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM EDEKA WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "sueddeutsche":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM SUEDDEUTSCHE WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "unileipzig":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM UNILEIPZIG WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "microsoft":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM MICROSOFT WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "zalando":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM ZALANDO WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "trivago":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM TRIVAGO WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "paypal":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM PAYPAL WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "apple":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM APPLE WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "rocketbeans":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM ROCKETBEANS WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "alternate":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM ALTERNATE WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "steam":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM STEAM WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "vine":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM VINE WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "burgerking":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM BURGERKING WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "amorelie":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM AMORELIE WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "payback":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM PAYBACK WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					case "subway":
						ps = db.connection.prepareStatement("SELECT CONTENT,DATE FROM SUBWAY WHERE DATE = ?");
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
						break;
					default:
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
			tl.getTexts("google", "June 9, 1999");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}