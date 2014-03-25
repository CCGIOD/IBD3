package servlets;

/*
 * @(#)NouvelleRepresentationServlet.java	1.0 2007/10/31
 * 
 * Copyright (c) 2007 Sara Bouchenak.
 */
import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDConnexion;
import bdd.accessBD.BDException;
import bdd.accessBD.BDRequetes;
import bdd.modeles.Spectacle;
import servlets.utils.ConvertHTML;

import java.io.IOException;
import java.util.Vector;

/**
 * NouvelleRepresentation Servlet.
 *
 * This servlet dynamically adds a new date a show.
 *
 * @author <a href="mailto:Sara.Bouchenak@imag.fr">Sara Bouchenak</a>
 * @version 1.0, 31/10/2007
 */

@SuppressWarnings("serial")
public class NouvelleRepresentationServlet extends HttpServlet {

	/**
	 * HTTP GET request entry point.
	 *
	 * @param req	an HttpServletRequest object that contains the request 
	 *			the client has made of the servlet
	 * @param res	an HttpServletResponse object that contains the response 
	 *			the servlet sends to the client
	 *
	 * @throws ServletException   if the request for the GET could not be handled
	 * @throws IOException	 if an input or output error is detected 
	 *				 when the servlet handles the GET request
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
			{
		String numS, dateS, heureS;
		ServletOutputStream out = res.getOutputStream();   

		res.setContentType("text/html");

		out.println("<HEAD><TITLE> Ajouter une nouvelle représentation </TITLE></HEAD>");
		out.println("<BODY bgproperties=\"fixed\" background=\"/images/rideau.JPG\">");
		out.println("<font color=\"#FFFFFF\"><h1> Ajouter une nouvelle représentation </h1>");

		numS		= req.getParameter("numS");
		dateS		= req.getParameter("date");
		heureS	= req.getParameter("heure");
		if ((numS == null || dateS == null || heureS == null) 
		   || (numS == "" && dateS == "" && heureS == ""))
				{
			out.println("<font color=\"#FFFFFF\">Veuillez saisir les informations relatives &agrave; la nouvelle représentation :");
			out.println("<P>");
			out.print("<form action=\"");
			out.print("NouvelleRepresentationServlet\" ");
			out.println("method=POST>");
			
			// Numero de spectacle.
			out.println("Numéro de spectacle :");
			if(numS != "" )
				out.println("<input type=text value=\"" + numS + "\" size=20 name=numS>");
			else
				out.println("<input type=text size=20 name=numS>");
			out.println("<br>");
			
			// Date de la représentation
			out.println("Date de la représentation :");
			if(dateS != "")
				out.println("<input type=text value=\"" + dateS + "\" size=20 name=date>");
			else
				out.println("<input type=text size=20 name=date>");
			out.println("<br>");
			
			// Heure de début de la représentation
			out.println("Heure de début de la représentation :");
			if(heureS != "")
				out.println("<input type=text value=\"" + heureS + "\" size=20 name=heure>");
			else
				out.println("<input type=text size=20 name=heure>");
			out.println("<br>");
			out.println("<input type=submit>");
			out.println("<br>");
			
			// Test s'il y avait une erreur.
			
			
			out.println("</form>");
		} else {
			// TO DO
			// Transformation des paramètres vers les types adéquats.
			// Ajout de la nouvelle représentation.
			// Puis construction dynamique d'une page web de réponse.
			try {
				Vector<Spectacle> spectacles = BDRequetes.getSpectables() ;
				out.println("<p><i><font color=\"#FFFFFF\">" + ConvertHTML.vectorSpectacleToHTML(spectacles)  + "</i></p>");
				
				// Test si le spectacle existe.

			} catch (BDException e) {
				out.println("<font color=\"#FFFFFF\"><h1>"+e.getMessage()+"</h1>");
			}

			out.println("<p><i><font color=\"#FFFFFF\">A compléter</i></p>");
			out.println("<p><i><font color=\"#FFFFFF\">...</i></p>");
		}

		out.println("<hr><p><font color=\"#FFFFFF\"><a href=\"/admin/admin.html\">Page d'administration</a></p>");
		out.println("<hr><p><font color=\"#FFFFFF\"><a href=\"/index.html\">Page d'accueil</a></p>");
		out.println("</BODY>");
		out.close();

			}

	/**
	 * HTTP POST request entry point.
	 *
	 * @param req	an HttpServletRequest object that contains the request 
	 *			the client has made of the servlet
	 * @param res	an HttpServletResponse object that contains the response 
	 *			the servlet sends to the client
	 *
	 * @throws ServletException   if the request for the POST could not be handled
	 * @throws IOException	   if an input or output error is detected 
	 *					   when the servlet handles the POST request
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
			{
		doGet(req, res);
			}


	/**
	 * Returns information about this servlet.
	 *
	 * @return String information about this servlet
	 */

	public String getServletInfo() {
		return "Ajoute une représentation à une date donnée pour un spectacle existant";
	}

}
