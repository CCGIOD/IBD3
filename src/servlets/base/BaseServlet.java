package servlets.base;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bdd.accessBD.BDConnexion;
import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

	protected ServletOutputStream out;
		
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);  
	    
		out = res.getOutputStream();
		res.setContentType("text/html");
		
	    if (session.getAttribute("config") == null) {  
	    	try {
				BDRequetes.checkCaddieLifetime();
				session.setAttribute("config", "checked");  
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}
	    } 
	}
	
	public boolean testConnection () throws IOException {
		Connection conn = null;
		boolean rep;
		try {
			conn = BDConnexion.getConnexion();
			rep = true;
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
			rep = false;
		}
		finally { BDConnexion.FermerTout(conn, null, null); }
		return rep;
	}
	
	public void header (String... str) throws IOException {
		String title, h1;
		if (str.length == 1)
			title = h1 = str[0];
		else{
			title = str[0];
			h1 = str[0];
		}
		
		out.println("<HEAD>");
		out.println("<TITLE>"+title+"</TITLE>");
		out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\">");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<h1>"+h1+" :</h1>");
	}
	
	public void footer () throws IOException {
		out.println("<hr><p><a href=\"/index.html\">Page d'accueil</a></p>");
		out.println("</BODY>");
		out.close();
	}
	
	public void redirect (HttpServletResponse res, String url) throws IOException {
		res.sendRedirect(url);
		out.close();
	}
}
