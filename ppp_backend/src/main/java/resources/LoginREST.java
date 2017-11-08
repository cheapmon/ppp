package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import tools.SecurityService;
import tools.Token;

@Path("security")
public class LoginREST {

	@GET
	@Path("login")
	public Response logIn(@QueryParam("pw") String password,
			@QueryParam("user") String user) {
		// TODO
		if (password.equals("test") && user.equals("user")) {
			Token token = tools.SecurityService.grantAccess();
			return Response.ok(token).build();
		} else {
			return Response.status(510).build();
		}
	}

	@GET
	@Path("logout")
	public Response logOut(@QueryParam("tokenId") String tokenId) {

		SecurityService.removeAccess(tokenId);
		return Response.ok().build();
	}

	@GET
	@Path("ping")
	public Response checkIfTokenIsValid(@QueryParam("tokenId") String tokenId) {

		if (SecurityService.checkAccess(tokenId)) {
			return Response.ok().entity(tokenId).build();

		} else {
			return Response.status(510).build();
		}
	}
}