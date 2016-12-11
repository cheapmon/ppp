/**
 * Provides interaction with ruby scripts for extracting privacy policies.
 * @author Simon Kaleschke
 */
public class PPManager {
	
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
                Process p = Runtime.getRuntime().exec("ruby crawl.rb " + s + " " + sys);
                p.waitFor();
                // Check if the extraction finished with errors.
                if(p.exitValue() == 0) {
                    System.out.println(s.toUpperCase() + " finished successfully!");
                } else {
                    System.out.println(s.toUpperCase() + " finished with an error!");
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }
    
}