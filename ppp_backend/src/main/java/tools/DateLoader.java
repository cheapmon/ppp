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
			String querie = "SELECT SYSTEM_DATE, DISPLAY_DATE FROM " + company.toUpperCase();
			rs = stmt.executeQuery(querie);
			if (rs == null){
				Date da = new Date(0,"Fehler: Unternehmen nicht vorhanden","");
				allDates.add(da);
			}
			else {
				int i = 1;
				while (rs.next()){
					Date da = new Date(i,rs.getString("SYSTEM_DATE"), rs.getString("DISPLAY_DATE"));
					allDates.add(da);
					//System.out.println(da);
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
