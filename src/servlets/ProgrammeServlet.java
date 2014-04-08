package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

import servlets.base.BaseServlet;
import servlets.utils.ConvertHTML;

import java.io.IOException;

/**
 * Servlet qui permet d'afficher le programme.
 */
@SuppressWarnings("serial")
public class ProgrammeServlet extends BaseServlet {

	/**
	 * Méthode doGet de la Servlet.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
		header("Programme de la saison");
		if (!testConnection()){ footer(); return; }	

		try {
			out.println(ConvertHTML.vectorProgrammeToHTML(BDRequetes.getRepresentations(null), false));
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
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
		return "Permet de voir le programme complet";
	}
}