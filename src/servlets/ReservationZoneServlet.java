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

		String nomS, numS, date, zone, c, cad_ok;
		nomS		= req.getParameter("nomS");
		numS		= req.getParameter("numS");
		date		= req.getParameter("date");
		zone		= req.getParameter("zone");
		c		= req.getParameter("c");
		cad_ok		= req.getParameter("cad_ok");

		if (numS != null && date != null && c != null && nomS != null){
			try {
				BDRequetes.addRepresentationCaddie(numS, date, c);
				res.sendRedirect("ReservationZoneServlet?numS="+numS+"&date="+date+"&nomS="+nomS+"&cad_ok="+c);
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}
		}

		if (numS == null || date == null || nomS == null)
			res.sendRedirect("ProgrammeServlet");
		else
			date=date.replaceAll("%20", " ");

		out.println("<h2> Pour la représentation du spectacle \""+nomS+"\" le "+date+" :</h2>");

		if (zone == null || zone == ""){		

			try {
				out.println("<p><i>"+ConvertHTML.vectorZoneToHTML(BDRequetes.getZones(),numS, nomS, date)+"</i></p>");
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}

		}

		if (cad_ok != null)
			out.println("<h4> Une place en zone "+cad_ok+" pour la représentation du spectacle \""+nomS+"\" le "+date+" a été ajoutée au caddie ! <i><a href=\"ConsultationCaddieServlet\">(Voir le caddie)</a></i></h4>");

		out.println("<p>A compléter ... <i>(réservation direct sans passer par le caddie)</i></p>");

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
