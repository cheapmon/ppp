package tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import representation.Date;

/**
 * SQL to receive all saved Dates for a company
 *
 */
public class DateLoader {
	
	/**
	 * get all dates of a company
	 * @param company: Table of the company in the database
	 * @return list of dates
	 * @throws SQLException
	 */
	public List<Date> getDates(String company) throws SQLException{
		//get database instance
		DatabaseInitializer db = DatabaseInitializer.getInstance();
		//set and initialize db connection
		db.connection = null;
		db.initDBConnection();
		//initialize result set, statement and list
		Statement stmt = null;
    	ResultSet rs = null;
    	List<Date> allDates = new ArrayList<Date>();
		try {
			stmt = db.connection.createStatement();
			//switch: SQL for company
			//20 standard companies
			//default for new companies
			switch (company){
				case "google":
					rs = stmt.executeQuery("SELECT DATE FROM GOOGLE");
					break;
				case "whatsapp":
					rs = stmt.executeQuery("SELECT DATE FROM WHATSAPP");
					break;
				case "wikimedia":
					rs = stmt.executeQuery("SELECT DATE FROM WIKIMEDIA");
					break;
				case "twitter":
					rs = stmt.executeQuery("SELECT DATE FROM TWITTER");
					break;
				case "edeka":
					rs = stmt.executeQuery("SELECT DATE FROM EDEKA");
					break;
				case "sueddeutsche":
					rs = stmt.executeQuery("SELECT DATE FROM SUEDDEUTSCHE");
					break;
				case "uni-leipzig":
					rs = stmt.executeQuery("SELECT DATE FROM UNI-LEIPZIG");
					break;
				case "microsoft":
					rs = stmt.executeQuery("SELECT DATE FROM MICROSOFT");
					break;
				case "zalando":
					rs = stmt.executeQuery("SELECT DATE FROM ZALANDO");
					break;
				case "trivago":
					rs = stmt.executeQuery("SELECT DATE FROM TRIVAGO");
					break;
				case "paypal":
					rs = stmt.executeQuery("SELECT DATE FROM PAYPAL");
					break;
				case "apple":
					rs = stmt.executeQuery("SELECT DATE FROM APPLE");
					break;
				case "rocketbeans":
					rs = stmt.executeQuery("SELECT DATE FROM ROCKETBEANS");
					break;
				case "alternate":
					rs = stmt.executeQuery("SELECT DATE FROM ALTERNATE");
					break;
				case "steam":
					rs = stmt.executeQuery("SELECT DATE FROM STEAM");
					break;
				case "vine":
					rs = stmt.executeQuery("SELECT DATE FROM VINE");
					break;
				case "burgerking":
					rs = stmt.executeQuery("SELECT DATE FROM BURGERKING");
					break;
				case "amorelie":
					rs = stmt.executeQuery("SELECT DATE FROM AMORELIE");
					break;
				case "payback":
					rs = stmt.executeQuery("SELECT DATE FROM PAYBACK");
					break;
				case "subway":
					rs = stmt.executeQuery("SELECT DATE FROM SUBWAY");
					break;
				default:
					String querie = "SELECT DATE FROM " + company.toUpperCase();
					rs = stmt.executeQuery(querie);
			}
			if (rs == null){
				Date da = new Date(0,"Fehler: Unternehmen nicht vorhanden");
				allDates.add(da);
			}
			else {
				int i = 1;
				//TODO get ID with SQL
				while (rs.next()){
					Date da = new Date(i,rs.getString("DATE"));
					allDates.add(da);
					System.out.println(da);
					i++;
				}
				i = 1;
			}
		} catch (SQLException e) {
			System.out.println("Fehler: Datenbankabfrage");
			e.printStackTrace();
		} 
		db.connection.close();
		return allDates;
	}
}