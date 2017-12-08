package resources;

import java.sql.SQLException;
import java.util.List;

import representation.Date;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import tools.DateLoader;




/**
 * sets up REST resource for dates
 *
 */
@Path("/date")
public class DateService {
	/**
	 * HTTP GET for texts
	 * @param company: Table of the company in the database
	 * @return JSON with dates
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Date> getDates(@QueryParam("company") String company) throws SQLException {
		DateLoader dl = new DateLoader();
		List<Date> allDates = dl.getDates(company);
		//System.out.println(allDates);
		return allDates;
	}
}
