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
public class ReservationZoneServlet extends HttpServlet {

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
		ServletOutputStream out = res.getOutputStream();   

		res.setContentType("text/html");

		out.println("<HEAD><TITLE> Reservation de places </TITLE><LINK rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\"></HEAD>");
		out.println("<BODY>");
		out.println("<h1> Reservation de places : </h1>");

		String numS, date, zone;
		numS		= req.getParameter("numS");
		date		= req.getParameter("date");
		zone		= req.getParameter("zone");


		if (numS == null || date == null)
			res.sendRedirect("ProgrammeServlet");
		else
			date=date.replaceAll("%20", " ");

		if (zone == null || zone == ""){		

			try {
				out.println("<p><i>"+ConvertHTML.vectorZoneToHTML(BDRequetes.getZones())+"</i></p>");
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}

		}
		
		out.println("<p>A compléter ...</p>");

		out.println("<hr><p><a href=\"/servlet/ProgrammeServlet\">Retour au programme complet</a></p>");
		out.println("<hr><p><a href=\"/index.html\">Page d'accueil</a></p>");
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
