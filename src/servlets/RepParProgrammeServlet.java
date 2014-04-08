package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

import servlets.base.BaseServlet;
import servlets.utils.ConvertHTML;

import java.io.IOException;

/**
 * Servlet qui permet d'afficher une représentation par programme.
 */
@SuppressWarnings("serial")
public class RepParProgrammeServlet extends BaseServlet {

	/**
	 * Méthode doGet de la Servlet.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
		header("Liste des représentations");
		if (!testConnection()){ footer(); return; }	

		String numS;
		numS = req.getParameter("numS");

		try {
			out.println("<p><i>" + ConvertHTML.vectorSpectacleConsultationToHTML(BDRequetes.getSpectables())  + "</i></p>");
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
		}

		if (numS != null) {
			try {
				out.println("<p><i>"+ConvertHTML.vectorProgrammeToHTML(BDRequetes.getRepresentations(numS), true)+"</i></p>");
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}
		} 

		footer();
	}

	/**
	 * Méthode doPost de la Servlet.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		doGet(req, res);
	}

	/**
	 * Méthode getServletInfo de la Servlet.
	 * @return L'info.
	 */
	public String getServletInfo() {
		return "Permet de voir les représentations d'un spectacle donné";
	}
}
