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
import javax.ws.rs.core.Response;
import org.json.*;

import tools.TextLoader;

/**
 * sets up REST resource for custom text uploads
 *
 */

@Path("{user}")
public class UploadService {


	@GET
	@Path("upload")
	public static Response uploadTexts(@QueryParam("date") String date, @QueryParam("link") String link,
			@QueryParam("user") String user, @QueryParam("text") String text)
			throws SQLException {
		TextLoader tl = new TextLoader();
    	System.out.println(text+ " rest");
    	System.out.println(link+ " rest");
    	System.out.println(date+ " rest");
    	System.out.println(user+ " rest");
		tl.setText(text,date,link,user);
		return Response.ok().build();
	}
	
	@GET
	@Path("load")
	@Produces(MediaType.APPLICATION_JSON)
	public static List <Texts> loadTexts(@PathParam("user") String user) throws SQLException{
		TextLoader tl = new TextLoader();
		List<Texts> allTexts = tl.loadTexts(user);
		return allTexts;
		
	}
	
	@GET
	@Path("remove")
	public static Response removeText(@PathParam("user") String user,@QueryParam("date") String date, @QueryParam("link") String link) throws SQLException{
		TextLoader tl = new TextLoader();
		tl.removeText(date, link, user);
		return Response.ok().build();
		
	}
}
