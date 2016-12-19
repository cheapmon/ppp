package server_core;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.ExtendedUriInfo;


/**
 * Filters all incoming requests. See
 * https://jersey.java.net/documentation/latest/filters-and-interceptors.html
 * for more information.
 * 
 * @author SB
 *
 */
public class RequestFilter implements ContainerResponseFilter {

	/**
	 * 'true' iff in debug mode, i.e., no security features are checkd.
	 */
	public static boolean debug = false;

	/**
	 * URI context.
	 */
	@Context
	private ExtendedUriInfo uriInfo;

	/**
	 * This filter gets invoked every time a request gets posted. It checks for
	 * security issues and set CORS headers.
	 * 
	 * @param request
	 *            Request Context.
	 * @param response
	 *            Response Context.
	 */
	public void filter(ContainerRequestContext request,
			ContainerResponseContext response) {

		// CORS
		response.getHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHeaders().add("Access-Control-Allow-Headers",
				"origin, content-type, accept, authorization, key");
		response.getHeaders().add("Access-Control-Allow-Credentials", "true");
		response.getHeaders().add("Access-Control-Allow-Methods",
				"GET, PUT, POST, DELETE, OPTIONS, HEAD");
		response.getHeaders().add("Access-Control-Expose-Headers", "key");

	}
}