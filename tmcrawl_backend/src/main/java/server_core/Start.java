package server_core;

/**
 * starts server and crawler in different threads
 *
 */
public class Start {
	public static void main(String[] args){
		System.out.println("Starting Server Thread...");
		ServerStarter server = new ServerStarter();
		server.start();
		System.out.println("Starting Crawler Thread...");
		CrawlerStarter crawler = new CrawlerStarter();
		crawler.start();
	}

}