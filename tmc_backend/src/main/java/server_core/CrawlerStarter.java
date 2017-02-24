package server_core;

import java.util.*;
import java.text.*;
import java.io.*;

/**
 * starts the crawling process
 *
 */
public class CrawlerStarter extends Thread {
    
    /** Time when crawler starts. */
    private int HOURS = 20;
    private int MIN = 5;
    
    /** List of all possible sites. */
    static String[] sites = {
    "alternate", "amorelie", "apple", "burgerking", "edeka", "google",
    "microsoft", "payback", "paypal", "rocketbeans", "steam", "subway",
    "sueddeutsche", "trivago", "twitter", "unileipzig", "vine",
    "whatsapp", "wikimedia", "zalando"
    };

    
    /**
     * Calls the ruby scripts.
     * @param update look for new entries (t) or fetch all (f)
     */
    public static void get(boolean update) {
        String sys;
        if(update) {
            sys = "update";
        } else {
            sys = "fetch";
        }
        // Call crawl.rb for every site.
        for(String s : sites) {
            try {
                Process p = Runtime.getRuntime().exec("ruby " + System.getProperty("user.home") +  "/html/tmc_extraction/src/Crawler.rb " + s + " " + sys);
                p.waitFor();
                // Check if the extraction finished with errors.
                if(p.exitValue() == 0) {
                    System.out.println(s.toUpperCase() + " finished successfully!");
                } else {
                    System.out.println(s.toUpperCase() + " finished with an error!");
					try (final BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
						String line;
						if ((line = b.readLine()) != null)
							System.out.println(line);
					} catch (final IOException e) {
						e.printStackTrace();
					} 
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }
    
    /** boolean to check if thread should terminate*/
    boolean running = true;
    
    /**
     * set boolean to false to stop thread
     *
     */
    public boolean terminate(){
        running = false;
        return running;
    }
    
    /**
     * runs the thread
     * task starts every day
     */
    public synchronized void run(){
        System.out.println("Crawler starts at: "
                           + ((HOURS < 10) ? "0" + HOURS : HOURS) + ":"
                           + ((MIN < 10) ? "0" + MIN : MIN));
        //check if running is true
        while (running){
            
            //set up time
            Date dt = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(dt);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            
            //check if is time to start the crawling process
            if(hours==HOURS && min == MIN){
                //asks wether policies.db existes, if not then get(false), if then get(true)
                System.out.println("Crawling in progress");
                get(true);
                System.out.println("Crawling finished");
            }
            //wait one minute
            try {
                wait(1000*60);
            } catch (InterruptedException e){
                System.out.println("Crawler interrupted");
            }
        }
        System.out.println("Crawler stopped");
    }
}
