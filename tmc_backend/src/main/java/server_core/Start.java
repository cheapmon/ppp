package server_core;

import java.io.IOException;
import java.awt.event.KeyEvent;

/**
 * starts server and crawler in different threads
 *
 */
public class Start {
    /**
     * main method to start crawler and server thread
     * @param args
     */
	public static void main(String[] args){
		
        //Thread: Server
        System.out.println("Starting Server Thread...");
		ServerStarter server = new ServerStarter();
		server.start();
        
        //Thread: Crawler
		System.out.println("Starting Crawler Thread...");
		CrawlerStarter crawler = new CrawlerStarter();
		crawler.start();
        
        //Server stopps when enter is pressed, implemented in ServerStarter
        //Crawler stopps when enter is pressed and Crawling Thread is sleeping
        System.out.println("============================================================");
        System.out.println("| Hit enter once to stop crawler, twice to stop the server |");
        System.out.println("|                                                          |");
        System.out.println("============================================================");
        
        //listen for key event to crawler
        int key = 0;
        while (key != KeyEvent.VK_ENTER){
            
            //listen
            try {
                key = System.in.read();
                System.out.println("If crawling is running, the crawler stops after the last task.");
            } catch (IOException e){
                e.printStackTrace();
            }
            
            //check if enter is pressed
            if (key == KeyEvent.VK_ENTER){
                crawler.terminate();
                synchronized(crawler){
                    crawler.notify();
                }
            }
        }
	}
}