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
public class ConfigurationServlet extends HttpServlet {

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

		out.println("<HEAD><TITLE> Configuration </TITLE><LINK rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\"></HEAD>");
		out.println("<BODY>");
		out.println("<h1> Configuration : </h1>");

		try {
			int d = BDRequetes.getCaddieLifetime();
			String d2;
			if (d == -1)
				d2 = "1 SESSION";
			else if (d == -2)
				d2 = "SANS LIMITE";
			else
				d2 = d+" JOURS";			
			
			out.println("<h2> La durée de vie du caddie actuelle est : "+d2+"</h2>");
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
		}

		out.println("<TABLE BORDER='1' width=\"800\"><CAPTION>Durée de vie du caddie :</CAPTION>");

		out.println("<TR><TH><a href=\"ConfigurationServlet?val=SESSION\">SESSION</a></TH>");
		out.println("<TH><form action=\"\"><input type=\"number\" min=\"1\" step=\"1\" pattern=\"\\d+\" name=\"val\"> <input type=\"submit\" value=\"Submit\"></form></TH>");
		out.println("<TH><a href=\"ConfigurationServlet?val=NOLIMIT\">SANS LIMITE</a></TH>");

		out.println("</TR></TABLE>");

		String val;
		val		= req.getParameter("val");

		if (val != null){
			boolean valide = false;
			if ((val.compareTo("SESSION") == 0 || val.compareTo("NOLIMIT") == 0))
				valide=true;
			else{
				try {
					int i = Integer.parseInt(val);
					if (i < 0)
						throw new Exception();
					else
						valide = true;
				} 
				catch (Exception e) {}
			}

			if (valide)
				out.println("<p>ok</p>");

		}

		out.println("<p>A compléter ... <i>(selection d'un nouveau choix de durée de vie)</i></p>");

		out.println("<hr><p><a href=\"/admin/admin.html\">Page d'administration</a></p>");
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
