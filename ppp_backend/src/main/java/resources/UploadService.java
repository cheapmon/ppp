package resources;

import representation.Texts;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.json.*;

import tools.TextLoader;

/**
 * sets up REST resource for custom text uploads
 *
 */

@Path("{user}")
public class UploadService {


	@POST
	@Path("upload")
	@Consumes(MediaType.APPLICATION_JSON)
	public static void uploadTexts(JsonObject inputText)
			throws SQLException {
		TextLoader tl = new TextLoader();
		String text = inputText.getString("text");
		String date = inputText.getString("date");
		String link = inputText.getString("link");
		String user = inputText.getString("user");
    	System.out.println(text+ " rest");
    	System.out.println(link+ " rest");
    	System.out.println(date+ " rest");
    	System.out.println(user+ " rest");
		tl.setText(text,date,link,user);

	}
	
	@GET
	@Path("load")
	@Produces(MediaType.APPLICATION_JSON)
	public static List <Texts> loadTexts(@PathParam("user") String user) throws SQLException{
		TextLoader tl = new TextLoader();
		List<Texts> allTexts = tl.loadTexts(user);
		return allTexts;
		
	}
}
