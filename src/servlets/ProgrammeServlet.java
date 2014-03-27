package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

import servlets.utils.ConvertHTML;

import java.io.IOException;

/**
 * Proramme Servlet.
 *
 * This servlet dynamically returns the theater program.
 *
 * @author <a href="mailto:Sara.Bouchenak@imag.fr">Sara Bouchenak</a>
 * @version 1.0, 31/10/2007
 */

@SuppressWarnings("serial")
public class ProgrammeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletOutputStream out = res.getOutputStream();   

		res.setContentType("text/html");

		out.println("<HEAD><TITLE> Programme de la saison </TITLE><LINK rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\"></HEAD>");
		out.println("<BODY bgproperties=\"fixed\" background=\"/images/rideau.JPG\" style=\"color:white;\">");
		out.println("<font color=\"#FFFFFF\"><h1>Programme de la saison :</h1>");		
		
		try {
			out.println("<p><i><font color=\"#FFFFFF\">"+ConvertHTML.vectorProgrammeToHTML(BDRequetes.getRepresentations(null))+"</i></p>");
		} catch (BDException e) {
			out.println("<font color=\"#FFFFFF\"><h1>"+e.getMessage()+"</h1>");
		}

		out.println("<hr><p><font color=\"#FFFFFF\"><a href=\"/index.html\">Accueil</a></p>");
		out.println("</BODY>");
		out.close();		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	/**
	 * Returns information about this servlet.
	 *
	 * @return String information about this servlet
	 */
	public String getServletInfo() {
		return "Retourne le programme du théatre";
	}

}