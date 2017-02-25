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
     * @param link: web link to the policy
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
            String querie = "SELECT CONTENT, SYSTEM_DATE, LINK FROM "+ company.toUpperCase() + " WHERE SYSTEM_DATE = ?";
            ps = db.connection.prepareStatement(querie);
            ps.setString(1, date);
            rs = ps.executeQuery();
            while (rs.next()){
                Texts t = new Texts(rs.getString(1),rs.getString(2), rs.getString(3));
                allDates.add(t);
                //System.out.println(t);
            }
        } catch (SQLException e) {
            System.out.println("Fehler: Datenbankabfrage");
            e.printStackTrace();
        } 
        db.connection.close();
        return allDates;
    }
}
