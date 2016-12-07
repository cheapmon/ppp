package resources;

import java.sql.SQLException;
import java.util.List;

import representation.Date;
import tools.DatabaseInitializer;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;

import tools.DateLoader;

import org.glassfish.jersey.*;


@Path("/datum")
public class DateService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Date> getDates(@QueryParam("company") String company) throws SQLException {
		DateLoader dl = new DateLoader();
		List<Date> allDates = dl.getDates(company);
		System.out.println(allDates);
		
		
		
		
		return allDates;
	}
	
public static void main (String[] args) throws SQLException{
	getDates("google");
}
}