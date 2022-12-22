package view;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.DBNotes;

public class CloseTicket extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBNotes.changeStatus(request.getParameter("id"));
		request.getRequestDispatcher("pages/redirect.html").include(request, response);
	}
}