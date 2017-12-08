package resources;

import representation.Texts;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import tools.TextLoader;

/**
 * sets up REST resource for custom text uploads
 *
 */

@Path("{user}")
public class UploadService {


	@GET
	@Path("upload")
	public static void uploadTexts(@PathParam("text") String text, @PathParam("link") String link,
			@PathParam("date") String date, @PathParam("user") String user)
			throws SQLException {
		TextLoader tl = new TextLoader();
		tl.setText(text, link, date, user);

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
