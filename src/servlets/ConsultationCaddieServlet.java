package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import servlets.base.BaseServlet;
import servlets.caddie.CaddieVirtuel;
import servlets.utils.ConvertHTML;
import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

import java.io.IOException;
import java.text.ParseException;

@SuppressWarnings("serial")
public class ConsultationCaddieServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException	{
		super.doGet(req, res);
		header("Consultation du caddie", "Consultation du caddie <i>("+session.getAttribute("config")+")</i>");
		if (!testConnection()){ footer(); return; }

		String idp, idm, idd;
		idp = req.getParameter("idp");
		idm = req.getParameter("idm");
		idd = req.getParameter("idd");

		if (idp != null && idm == null && idd == null){
			if (session.getAttribute("config").toString().compareTo("P") == 0)
				try {
					BDRequetes.setQtCaddie(idp, '+');
					redirect(res, "ConsultationCaddieServlet");
					return;
				} catch (BDException e) {
					out.println("<h1>"+e.getMessage()+"</h1>");
				}
			else
				try {
					CaddieVirtuel.setQt(idp, '+');
				} catch (ParseException e) {
					redirect(res, "ConsultationCaddieServlet");
					return;
				}
		}
		else if (idp == null && idm != null && idd == null){
			if (session.getAttribute("config").toString().compareTo("P") == 0)
				try {
					BDRequetes.setQtCaddie(idm, '-');
					redirect(res, "ConsultationCaddieServlet");
					return;
				} catch (BDException e) {
					out.println("<h1>"+e.getMessage()+"</h1>");
				}
			else
				try {
					CaddieVirtuel.setQt(idm, '-');
				} catch (ParseException e) {
					redirect(res, "ConsultationCaddieServlet");
					return;
				}
		}
		else if (idp == null && idm == null && idd != null){
			if (session.getAttribute("config").toString().compareTo("P") == 0)
				try {
					BDRequetes.setQtCaddie(idd, 'd');
					redirect(res, "ConsultationCaddieServlet");
					return;
				} catch (BDException e) {
					out.println("<h1>"+e.getMessage()+"</h1>");
				}
			else
				try {
					CaddieVirtuel.setQt(idd, 'd');
				} catch (ParseException e) {
					redirect(res, "ConsultationCaddieServlet");
					return;
				}
		}

		if (session.getAttribute("config").toString().compareTo("P") == 0){
			try {
				out.println("<p><i>" + ConvertHTML.vectorCaddieToHTML(BDRequetes.getContenuCaddie())  + "</i></p>");
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}
		}
		else{
			out.println("<p><i>" + ConvertHTML.vectorCaddieToHTML(CaddieVirtuel.getList())  + "</i></p>");
		}

		footer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		doGet(req, res);
	}

	public String getServletInfo() {
		return "Ajoute une représentation à une date donnée pour un spectacle existant";
	}
}
