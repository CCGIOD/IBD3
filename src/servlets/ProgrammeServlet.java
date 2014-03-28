package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

import servlets.utils.ConvertHTML;

import java.io.IOException;

@SuppressWarnings("serial")
public class ProgrammeServlet extends _BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
		header("Programme de la saison");
		if (!testConnection()){ footer(); return; }	
		
		try {
			out.println("<p><i>"+ConvertHTML.vectorProgrammeToHTML(BDRequetes.getRepresentations(null), false)+"</i></p>");
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
		}

		footer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		doGet(req, res);
	}

	public String getServletInfo() {
		return "Permet de voir le programme complet";
	}
}