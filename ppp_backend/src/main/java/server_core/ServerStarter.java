package server_core;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.net.InetAddress;

import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * starts a grizzly server on localhost:8080
 *
 */
public class ServerStarter extends Thread {
    /** Base URI the Grizzly HTTP server will listen on */
    public static final String BASE_URI = "http://localhost:8080/rest/";
    
    /**
     * starts the server
     *
     */
    public void run(){
        //server is build
        final HttpServer server = startServer();
        System.out.println(BASE_URI);
        System.out.println(String.format("Jersey app started with WADL available at "
                                         + "%sapplication.wadl\n...", BASE_URI));
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //shutdown server
        server.shutdown();
    }
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.underdog.jersey.grizzly package
        // JacksonFeature allows the export of JSONs to the resources
        final ResourceConfig rc = new ResourceConfig().packages("resources").register(RequestFilter.class);
        rc.register(JacksonFeature.class);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
    
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        
        System.out.println(String.format("Jersey app started with WADL available at "
                                         + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
        System.out.println("Server stopped.");
    }
    
}