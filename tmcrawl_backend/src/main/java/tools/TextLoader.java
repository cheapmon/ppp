package tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import representation.Texts;

/**
 * SQL to receive saved Texts for a company
 *
 */
public class TextLoader {
	/**
	 * get text of a company
	 * @param company: Table of the company in the database
	 * @param date: searched date of the saved policy
	 * @return list of texts
	 * @throws SQLException
	 */
	public List<Texts> getTexts(String company, String date) throws SQLException{
		//get database instance
		DatabaseInitializer db = DatabaseInitializer.getInstance();
		//set and initialize db connection
		db.connection = null;
		db.initDBConnection();
		//initialize result set, statement and list
		PreparedStatement ps = null;
    	ResultSet rs;
    	List<Texts> allDates = new ArrayList<Texts>();
   		try {
			//switch: SQL for company
			//20 standard companies
			//default for new companies
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
						String querie = "SELECT CONTENT, DATE FROM "+ company.toUpperCase() + " WHERE DATE = ?";
						ps = db.connection.prepareStatement(querie);
				        ps.setString(1, date); 
				          
				        rs = ps.executeQuery(); 
						while (rs.next()){
							Texts t = new Texts(rs.getString(1),rs.getString(2));
							allDates.add(t);
							System.out.println(t);

						}
				}
		} catch (SQLException e) {
			System.out.println("Fehler: Datenbankabfrage");
			e.printStackTrace();
		} 
		db.connection.close();
		return allDates;
	}
}