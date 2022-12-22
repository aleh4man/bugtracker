package view;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.DBNotes;
import model.DBUser;

public class MainPage extends HttpServlet{
	public void init(){
		DBNotes.init();
		DBUser.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showPage(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showPage(request, response);
	}

	private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("name");
		if (name == null) response.sendRedirect("pages/index.html");
		else{
			response.setCharacterEncoding("UTF-8");

			PrintWriter out = response.getWriter();
			out.println("<html>");

			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<link rel=\"stylesheet\" href=\"pages/styles/general.css\">");
			out.println("<link rel=\"stylesheet\" href=\"pages/styles/table.css\">");
			out.println("</head>");
			out.println("<body>");
			request.getRequestDispatcher("pages/header.html").include(request, response);
			out.println("<div class = \"content\">");
			out.println(DBNotes.getTableHTML());
			out.println("</div");
			out.println("</body>");
			out.println("</html>");
		}
	}
}
