package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import tools.SecurityService;
import tools.UserDataService;
import tools.Token;

@Path("security")
public class LoginREST {

	@GET
	@Path("login")
	public Response logIn(@QueryParam("pw") String password, @QueryParam("user") String user) {
		// TODO
		if (UserDataService.checkUser(user, password)) {
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

	@GET
	@Path("changeData")
	public Response changeUserData(@QueryParam("tokenId")String tokenId, @QueryParam("user")String user,
			@QueryParam("pw")String password, @QueryParam("targetUser")String targetUser,
			@QueryParam("targetPw")String targetPw, @QueryParam("operation")String operation ) {
		
		Response response = Response.status(510).build();
		
		if(SecurityService.checkAccess(tokenId) && UserDataService.checkUser(user, password)){
			
			switch(operation) {
			case("add"):
				if(user.toLowerCase().equals("admin")) {
					UserDataService.createUser(targetUser, targetPw);
					response = Response.ok().build();
					break;
				} else {
					System.out.println("Rechte nicht ausreichend!");
					response = Response.status(510).build();
					break;
				}
			case("del"):
				if(user.toLowerCase().equals("admin")) {
					UserDataService.deleteUser(targetUser);
					response = Response.ok().build();
					break;
				} else {
					System.out.println("Rechte nicht ausreichend!");
					response = Response.status(510).build();
					break;
				}
			case("edit"):
				if(user.toLowerCase().equals("admin") || user.toLowerCase().equals(targetUser.toLowerCase())) {
					UserDataService.editPw(targetUser, targetPw);
					response = Response.ok().build();
					break;
				} else {
					System.out.println("Rechte nicht ausreichend!");
					response = Response.status(510).build();
					break;
				}
			default:
				response = Response.status(510).build();
				break;
			}	
			return response;

		}else {
			return Response.status(510).build();
		}
	}
}