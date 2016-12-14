package resources;

import java.sql.SQLException;
import java.util.List;

import representation.Texts;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import tools.TextLoader;


@Path("/text")
public class TextService{
	
	@GET
	@Path("{company}")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Texts> getTexts(@PathParam("company") String company, @QueryParam("date") String date) throws SQLException {
		TextLoader tl = new TextLoader();
		List<Texts> allTexts = tl.getTexts(company, date);
		
		
		return allTexts;
	}
	
	
	
}