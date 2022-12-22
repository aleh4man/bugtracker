package model;
import java.util.*;
import java.io.*;

public class DBNotes{
	private static int id;

	public static void init(){
		try{
			File f = new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/notes.txt");
			if (!f.exists()){
				id = 0;
				f.createNewFile();
			}
			else{
				Scanner sc = new Scanner(f);
				String id_str = "";
				while (sc.hasNextLine()){
					String str = sc.nextLine();
					int idx = str.indexOf(':');
					if (idx < 0) continue;
					String tag = str.substring(0, idx);
					if (tag.equals("ID")) id_str = str.substring(idx+2);
				}
				id = Integer.parseInt(id_str);
				id++;
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	public static boolean hasFile(){
		try{
			File f = new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/notes.txt");
			if (f.exists()){
				return true;
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return false;
	}

	synchronized public static void addNote(String atr, String hdr, String trkr, String rsp, String dscrpt){
		try {
			int idx = hdr.indexOf('\n');
			if (idx > 0) hdr = hdr.substring(0, idx);
			dscrpt = dscrpt.replaceAll("\n", "\t");
			FileWriter pr = new FileWriter(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/notes.txt"), true);
			pr.write("ID: "+ Integer.toString(id++)+ "\n");
			pr.write("Autor: "+ atr + "\n");
			Calendar c = Calendar.getInstance();
			pr.write("Date: " + c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR) + "." + c.get(Calendar.MINUTE) + "." + c.get(Calendar.SECOND) + "\n");
			pr.write("Title: " + hdr + "\n");
			pr.write("Tracker: " + trkr + "\n");
			pr.write("Status: open\n");
			pr.write("Responsible: " + rsp + "\n");
			pr.write("Description: " + dscrpt + "\n");
			pr.write("\n");
			pr.flush();
        }
		catch (Exception e){
			System.out.println(e.toString());
		}
	}



	synchronized public static String getTableHTML(){
		StringBuilder sb = new StringBuilder();
		try {
			Scanner sc = new Scanner(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/notes.txt"));
			sb.append("<table>\n");
			
			sb.append("<tr id = \"h\">\n");
			sb.append("<td>ID</td>\n");
			sb.append("<td>Автор</td>\n");
			sb.append("<td>Тема</td>\n");
			sb.append("<td>Трекер</td>\n");
			sb.append("<td>Статус</td>\n");
			sb.append("</tr>\n");

			while (sc.hasNextLine()){
				String str = sc.nextLine();
				int idx = str.indexOf(':');
				if (idx < 0) {
					sb.append("</tr>\n");
					continue;
				}

				String tag = str.substring(0, idx);
				if (tag.equals("ID")){
					sb.append("<tr>\n");
					sb.append("<td>");
					sb.append("<form action=\"ticket\" method=\"get\">");
					sb.append("<button type=\"submit\" name=\"id\" value = \"" + str.substring(idx+2) + "\">" + str.substring(idx+2) + "</button>");
					sb.append("</form>");
					sb.append("</td>");
					continue;
				}
				if (tag.equals("Date") || tag.equals("Responsible") || tag.equals("Description")) continue;
				sb.append("<td>" + str.substring(idx+2) + "</td>\n");
			}
			sb.append("</table>\n");
		}
		catch (Exception e){
			return "\n";
		}
		return sb.toString();
	}



	synchronized public static String getNoteHTML(String id, String user){
		StringBuilder sb = new StringBuilder();
		String[] data = new String[8];
		try {
			Scanner sc = new Scanner(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/notes.txt"));
			boolean found = false;
			while (sc.hasNextLine()){
				String str = sc.nextLine();
				int idx = str.indexOf(':');
				if (idx < 0) continue;
				String tag = str.substring(0, idx);
				if (tag.equals("ID")){
					if (str.substring(idx+2).equals(id)){
						data[0] = str.substring(idx+2);
						found = true;
						break;
					}
				}
			}

			if (found){
				for (byte i = 1; i < 8; i++){
					String str = sc.nextLine();
					int idx = str.indexOf(':');
					data[i] = str.substring(idx+2);
				}
			}
			else return "Not found";

			sb.append("<h1>" + data[4]+ ": " + data[3] + " (" +  data[0] +")</h1>");
			sb.append("<h2>" + data[1] + ", " + data[2] + "</h2>");
			sb.append("<h2>Ответственный за выполнение: "+ data[6] + "<h2>");
			sb.append("<p>"+ data[7] + "</p>");
			sb.append("<h2> Status: " + data[5] + "</h2>");
			if (user.equals(data[1]) && data[5].equals("open")){
				sb.append("<form action=\"closeticket\" method=\"get\">");
				sb.append("<button type=\"submit\" name=\"id\" value = \"" + id + "\">Закрыть тикет</button>");
				sb.append("</form>");
			}
		}
		catch (Exception e){
			return e.toString();
		}
		return sb.toString();
	}

	synchronized public static void changeStatus(String id){
		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		try {
			Scanner sc = new Scanner(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/notes.txt"));
			while (sc.hasNextLine()){
				String str = sc.nextLine();
				int idx = str.indexOf(':');
				if (idx < 0) continue;
				String tag = str.substring(0, idx);
				if (tag.equals("ID")){
					if (str.substring(idx+2).equals(id)){
						for (byte i = 0; i < 8; i++){
							idx = str.indexOf(':');
							tag = str.substring(0, idx);
							if (tag.equals("Status")){
								str = str.replace("open", "close");
							}
							sb.append(str+"\n");
							str = sc.nextLine();
						}
						sb.append(str+"\n");
						continue;
					}
				}
				sb.append(str+"\n");
			}
			String s1 = sb.toString();
			FileWriter pr = new FileWriter(new File("/home/alexey/snap/apache-tomcat-9.0.68/webapps/bugtracker/WEB-INF/data/notes.txt"), false);
			
			pr.write(s1);
			pr.flush();
		}
		catch (Exception e){
			
		}
	}
}