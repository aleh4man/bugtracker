package model;
import java.util.*;
import java.io.*;

public class DBUser{
	public static void init(){
		try{
			File f = new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/users.txt");
			if (!f.exists()){
				f.createNewFile();
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	public static boolean hasFile(){
		try{
			File f = new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/users.txt");
			if (f.exists()){
				return true;
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return false;
	}

	synchronized public static boolean isEqual(String name, String password){
		try {
			Scanner sc = new Scanner(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/users.txt"));
			while (sc.hasNextLine()){
					String str = sc.nextLine();
					int idx = str.indexOf(':');
					String n = str.substring(0, idx).toLowerCase();
					String pw = str.substring(idx+1).toLowerCase();
					if (n.equals(name.toLowerCase()) || pw.equals(password.toLowerCase())) return true;
				}
        	}
		catch (Exception e){
			System.out.println(e.toString());
		}
		return false;
	}
	
	synchronized public static boolean hasSuchUser(String name, String password){
		try {
			Scanner sc = new Scanner(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/users.txt"));
			while (sc.hasNextLine()){
					String str = sc.nextLine();
					int idx = str.indexOf(':');
					String n = str.substring(0, idx);
					String pw = str.substring(idx+1);
					if (n.equals(name) && pw.equals(password)) return true;
				}
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
		return false;
	}
	
	synchronized public static boolean isCorrectName(String name){
		String n = name.toLowerCase();
		for (int i = 0; i < n.length(); i++){
        		char s = n.charAt(i);
        		if ((s < 'a') && (s > 'z')){
				return false;
        		}
        	}
        	return true;
	}
	
	synchronized public static void addNewUser(String name, String password){
		try {
			FileWriter pr = new FileWriter(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/users.txt"), true);
			pr.write(name + ":" + password + "\n");
			pr.flush();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	synchronized public static String getUsersOptions(String autor){
		StringBuilder sb = new StringBuilder();
		try{
			Scanner sc = new Scanner(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/users.txt"));
			while (sc.hasNextLine()){
				String tmp = sc.nextLine();
				int idx = tmp.indexOf(':');
				tmp = tmp.substring(0, idx);
				if (tmp.equals(autor)) continue;
				sb.append("<option>"+ tmp +"</option>");
			}
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
		return sb.toString();
	}
}