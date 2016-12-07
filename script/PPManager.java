public class PPManager {
	
    static String[] sites = {
        "alternate", "amorelie", "apple", "burgerking", "edeka", "google",
        "microsoft", "payback", "paypal", "rocketbeans", "steam", "subway",
        "sueddeutsche", "trivago", "twitter", "unileipzig", "vine",
        "whatsapp", "wikimedia", "zalando"
    };

    public static void get(boolean update) {
        String sys;
        if(update) {
            sys = "update";
        } else {
            sys = "fetch";
        }
        for(String s : sites) {
            try {
                Process p = Runtime.getRuntime().exec("ruby crawl.rb " + s + " " + sys);
                p.waitFor();
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