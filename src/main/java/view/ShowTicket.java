package view;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.DBNotes;

public class ShowTicket extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("name");
		String id = request.getParameter("id");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"pages/styles/general.css\">");
		out.println("<link rel=\"stylesheet\" href=\"pages/styles/ticket.css\">");
		out.println("<meta charset=\"UTF-8\">");
		out.println("</head>");
		out.println("<body>");
		request.getRequestDispatcher("pages/header.html").include(request, response);
		out.println("<div class = \"content\">");
		out.println(DBNotes.getNoteHTML(id, name));
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}