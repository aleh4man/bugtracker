package view;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.DBUser;

public class LogIn extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("login");
        String password = request.getParameter("password");

        if (DBUser.hasSuchUser(name, password)){
        	HttpSession session = request.getSession();
        	session.setAttribute("name", name);
        }
        else{
        	request.setAttribute("status", "error");
        }
        request.getRequestDispatcher("pages/redirect.html").include(request, response);
	}
}