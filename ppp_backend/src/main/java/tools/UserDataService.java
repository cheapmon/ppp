package tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import representation.Texts;

/**
 * handle the user database deletes and adds users changes passwords
 * 
 * @author AlexAG
 *
 */
public class UserDataService {

	public static void createUser(String name, String pw) {
		// get database instance
		DatabaseInitializer db = DatabaseInitializer.getInstance();
		// set and initialize db connection
		db.connection = null;
		db.initDBConnection();
		// initialize result set, statement and list
		PreparedStatement ps = null;
		// encrypt password
		String hashedPw = BCrypt.hashpw(pw, BCrypt.gensalt());
		try {
			String insert = "INSERT INTO login(user,password) VALUES(?,?)";
			ps = db.connection.prepareStatement(insert);
			ps.setString(1, name.toLowerCase());
			ps.setString(2, hashedPw);
			ps.executeUpdate();
			db.connection.close();
		} catch (SQLException e) {
			System.out.println("Fehler: Datenbankabfrage");
			e.printStackTrace();
		}
		DatabaseInitializer.createTable(name.toLowerCase());
	}

	/**
	 * check whether user and password are right or not
	 * 
	 * @param name
	 * @param pw
	 * @return
	 */
	public static boolean checkUser(String name, String pw) {
		// get database instance
		DatabaseInitializer db = DatabaseInitializer.getInstance();
		// set and initialize db connection
		db.connection = null;
		db.initDBConnection();
		// initialize result set, statement and list
		PreparedStatement ps = null;
		ResultSet rs = null;
		// encrypted password
		String storedPassword = null;
		try {
			String querie = "SELECT password FROM login WHERE user = ?";
			ps = db.connection.prepareStatement(querie);
			ps.setString(1, name.toLowerCase());
			rs = ps.executeQuery();
			storedPassword = rs.getString(1);
		} catch (SQLException e) {
			System.out.println("Fehler: Datenbankabfrage");
			e.printStackTrace();
		}

		if (BCrypt.checkpw(pw, storedPassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void editPw(String targetUser, String targetPw) {
		// get database instance
		DatabaseInitializer db = DatabaseInitializer.getInstance();
		// set and initialize db connection
		db.connection = null;
		db.initDBConnection();
		// initialize result set, statement and list
		PreparedStatement ps = null;
		String hashedPw = BCrypt.hashpw(targetPw, BCrypt.gensalt());
		try {
			String insert = "UPDATE login SET password = ?, "
					+ "WHERE user = ?";
			ps = db.connection.prepareStatement(insert);
			ps.setString(1, hashedPw);
			ps.setString(2, targetUser);
			ps.executeUpdate();
			db.connection.close();
		} catch (SQLException e) {
			System.out.println("Fehler: Datenbankabfrage");
			e.printStackTrace();
		}
	}

	/**
	 * used to delete a user
	 * @param user can not be "admin"
	 */
	public static void deleteUser(String user) {
		if (!user.equals("admin")) {
			// get database instance
			DatabaseInitializer db = DatabaseInitializer.getInstance();
			// set and initialize db connection
			db.connection = null;
			db.initDBConnection();
			// initialize result set, statement and list
			PreparedStatement ps = null;
			try {
				String querie = "DELETE FROM login WHERE user = ?";
				ps = db.connection.prepareStatement(querie);
				ps.setString(1, user);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Fehler: Datenbankabfrage");
				e.printStackTrace();
			}
			DatabaseInitializer.deleteTable(user);
		}else {
			System.out.println("One does not simply remove the admin.");
		}
	}
}
