package view;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.DBNotes;

public class CheckNewTicket extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("name");
		String title = request.getParameter("title");
		String tracker = request.getParameter("tracker");
		String description = request.getParameter("description");
		String responsible = request.getParameter("responsible");
		if ((title.length() > 5) && (description.length() > 5)){
			DBNotes.addNote(name, title, tracker, responsible, description);
		}
		
		request.getRequestDispatcher("pages/redirect.html").include(request, response);
	}
}