package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

import servlets.utils.ConvertHTML;

import java.io.IOException;

@SuppressWarnings("serial")
public class PlacesRepresentationServlet extends _BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
		header("Programme de la saison");
		if (!testConnection()){ footer(); return; }	

		String numS, date;
		numS = req.getParameter("numS");
		date = req.getParameter("date");
		
		if (numS == null || date == null){
			redirect(res, "ProgrammeServlet");
			return;
		}
		else
			date=date.replaceAll("%20", " ");
		
		try {
			out.println("<p><i>"+ConvertHTML.vectorPlaceToHTML(BDRequetes.getPlacesDisponibles(numS, date))+"</i></p>");
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
		}

		footer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		doGet(req, res);
	}

	public String getServletInfo() {
		return "Permet de voir les places disponible pour une représentation d'un spectacle donné";
	}
}