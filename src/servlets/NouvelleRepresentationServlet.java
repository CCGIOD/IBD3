package servlets;

/*
 * @(#)NouvelleRepresentationServlet.java	1.0 2007/10/31
 * 
 * Copyright (c) 2007 Sara Bouchenak.
 */
import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;
import bdd.exceptions.BDExceptionParamError;
import servlets.utils.ConvertHTML;

import java.io.IOException;

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
			throws ServletException, IOException{
		String numS, dateS, heureS;
		ServletOutputStream out = res.getOutputStream();   

		res.setContentType("text/html");

		out.println("<HEAD><TITLE> Ajouter une nouvelle représentation </TITLE><LINK rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\"></HEAD>");
		out.println("<BODY bgproperties=\"fixed\" background=\"/images/rideau.JPG\" style=\"color:white;\">");
		out.println("<font color=\"#FFFFFF\"><h1> Ajouter une nouvelle représentation </h1>");
		
		try {
			out.println("<p><i><font color=\"#FFFFFF\">" + ConvertHTML.vectorSpectacleToHTML(BDRequetes.getSpectables())  + "</i></p>");
		} catch (BDException e) {
			out.println("<font color=\"#FFFFFF\"><h1>"+e.getMessage()+"</h1>");
		}

		numS = req.getParameter("numS");
		dateS = req.getParameter("date");
		heureS = req.getParameter("heure");

		boolean error = false ;

		// Cas de l'insertion dans la base de données
		if ((numS != null && dateS != null && heureS != null) 
				&& (numS != "" && dateS != "" && heureS != ""))
		{
			
			String nomSpectacle = null ;
					try {
						nomSpectacle = BDRequetes.insertRepresentation(numS, dateS, heureS);
					} catch(BDExceptionParamError e1){
						out.println("<font color=\"#FFFFFF\"><h1>"+e1.getMessageError()+"</h1>");
						if(e1.getParamsError().contains(1)){
							numS="";
							error = true ;
						}
						if(e1.getParamsError().contains(2)){
							dateS="";
							error = true ;
						}
						if(e1.getParamsError().contains(3)){
							heureS="";
							error = true ;
						}
					} catch (BDException e2) {
						out.println("<font color=\"#FFFFFF\"><h1>"+e2.getMessage()+"</h1>");
						error = true ;
					}


			if(!error){
				out.println("<p><i><font color=\"#FFFFFF\">Vous venez d'ajouter la représentation suivante :</i></p>");
				out.println("<p><i><font color=\"#FFFFFF\">Pour le spectacle "+nomSpectacle + " (" + numS + ")" +" à l'horaire " + dateS + " " + heureS +"</i></p>");
				out.println("<p>Ajouter une autre représentation <a href=\"/servlet/NouvelleRepresentationServlet?numS="+numS+"\">à ce spectacle</a> ou <a href=\"/servlet/NouvelleRepresentationServlet\">à un autre spectacle</a></p>");

			}
		}

		// Cas affichage du formule (première fois ou error).
		if (((numS == null || dateS == null || heureS == null) 
				|| (numS == "" || dateS == "" || heureS == "")) || error )
		{

			out.println("<font color=\"#FFFFFF\">Veuillez saisir les informations relatives &agrave; la nouvelle représentation :");
			out.println("<P>");
			out.print("<form action=\"");
			out.print("NouvelleRepresentationServlet\" ");
			out.println("method=POST>");

			// Numero de spectacle.
			out.println("Numéro de spectacle :");
			if(numS != "" &&  numS != null )
				out.println("<input type=text value=\"" + numS + "\" size=20 name=numS>");
			else
				out.println("<input type=text size=20 name=numS>");
			out.println("<br>");

			// Date de la représentation
			out.println("Date de la représentation :");
			if(dateS != "" &&  dateS != null)
				out.println("<input type=text value=\"" + dateS + "\" size=20 name=date>");
			else
				out.println("<input type=text size=20 name=date>");
			out.println("<br>");

			// Heure de début de la représentation
			out.println("Heure de début de la représentation :");
			if(heureS != "" &&  heureS != null)
				out.println("<input type=text value=\"" + heureS + "\" size=20 name=heure>");
			else
				out.println("<input type=text size=20 name=heure>");
			out.println("<br>");
			out.println("<input type=submit>");
			out.println("<br>");

			// Test s'il y avait une erreur.
			if(numS == "" || dateS == "" || heureS == ""){
				out.println("<font color=\"#FF0000\">Les informations que vous avez fourni sont incorrect");
			}
			out.println("</form>");	
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
