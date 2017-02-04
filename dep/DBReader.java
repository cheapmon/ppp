import java.util.ArrayList;
import java.io.*;
import java.lang.StringBuilder;

public class DBReader {
	
	public static String[] dates(String table) {
		ArrayList<String> datelist = new ArrayList();
		try {
            Process p = Runtime.getRuntime().exec("ruby read.rb dates " + table);
            p.waitFor();
            BufferedReader processIn = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = processIn.readLine()) != null) {
                datelist.add(line);
            } 
        } catch (Exception err) {
            err.printStackTrace();
        }
        String[] dates = datelist.toArray(new String[datelist.size()]);
        return dates;
	}

	public static String content(String table, String date) {
        StringBuilder plain = new StringBuilder();
        String line;
		try {
            Process p = Runtime.getRuntime().exec("ruby read.rb content " + table + " " + date);
            p.waitFor();
            BufferedReader processIn = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while((line = processIn.readLine()) != null) {
                plain.append(line).append("\n");
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return plain.toString();
	}

}