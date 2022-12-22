package view;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.DBUser;

public class CreateTicketForm extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		//request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("name");
		String names = DBUser.getUsersOptions(name);
		request.setAttribute("name", name);
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<link rel=\"stylesheet\" href=\"pages/styles/general.css\">");
		out.println("<link rel=\"stylesheet\" href=\"pages/styles/form.css\">");
		out.println("</head>");
		out.println("<body>");
		if (!names.isEmpty()){
			request.getRequestDispatcher("pages/header.html").include(request, response);
			out.println("<div class = \"content\">");
			out.println("<form action = \"checknew\" method = \"post\">");
			request.getRequestDispatcher("pages/add_part.html").include(request, response);
			out.println("<div class = \"frm\">");
			out.println("<span id =\"log_text\">Назначить</span>");
			out.println("<select name = \"responsible\">");
			out.println(names);
			out.println("</select>");
			out.println("</div>");
			out.println("<input id = \"push\"type=\"submit\" value=\"Отправить\">");
			out.println("</form>");
			out.println("</div>");
		}
		else {
			request.getRequestDispatcher("pages/redirect.html").include(request, response);
		}
		out.println("</body>");
		out.println("</html>");
	}
}