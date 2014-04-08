package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import servlets.base.BaseServlet;
import servlets.caddie.CaddieVirtuel;
import servlets.utils.ConvertHTML;
import bdd.accessBD.BDRequetes;
import bdd.accessBD.BDRequetesTest;
import bdd.exceptions.BDException;
import bdd.modeles.Caddie;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

@SuppressWarnings("serial")
public class ConsultationCaddieServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException	{
		super.doGet(req, res);
			header("Consultation du caddie");
		if (!testConnection()){ footer(); return; }

		String idp, idm, idd, valider;
		idp = req.getParameter("idp");
		idm = req.getParameter("idm");
		idd = req.getParameter("idd");
		idd = req.getParameter("idd");
		valider = req.getParameter("valider");

		if(valider != null){
			if (session.getAttribute("config").toString().compareTo("P") == 0){
				try {
					Vector<Caddie> caddie = BDRequetes.getContenuCaddie() ;
					Vector<Caddie> deleteC = null ;
					try {
						deleteC = BDRequetesTest.testCheckValideCaddie(caddie, 1);
					} catch (ParseException e1) {
					}
					if(deleteC != null && deleteC.size() > 0){
						out.println("<p><i>" + ConvertHTML.vectorCaddieDeletionToHTML(deleteC)+ "</i></p>");
					}
					if(caddie.size()-deleteC.size()>0){
						try {
							out.println("<p><i>" + ConvertHTML.vectorTicketsToHTML(BDRequetes.valideCaddie(BDRequetes.getContenuCaddie())));
							BDRequetes.deleteCaddie();
						} catch (ParseException e) {
						}
					}
				} catch (BDException e) {
					out.println("<h1>"+e.getMessage()+"</h1>");
				}
			}
			else
			{
				try {
					Vector<Caddie> caddie = CaddieVirtuel.getList();
					Vector<Caddie> deleteC = null ;
					try {
						deleteC = BDRequetesTest.testCheckValideCaddie(caddie, 2);
					} catch (ParseException e1) {
					}
					if(deleteC != null && deleteC.size() > 0){
						out.println("<p><i>" + ConvertHTML.vectorCaddieDeletionToHTML(deleteC) + "</i></p>");
					}
					if(CaddieVirtuel.getList().size()>0){
						try {
							out.println("<p><i>" + ConvertHTML.vectorTicketsToHTML(BDRequetes.valideCaddie(CaddieVirtuel.getList())));
							CaddieVirtuel.vider() ;
							
						} catch (ParseException e) {
						}
					}
				} catch (BDException e) {
					out.println("<h1>"+e.getMessage()+"</h1>");
				}
			}
		}
		
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
					redirect(res, "ConsultationCaddieServlet");
					return;
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
					redirect(res, "ConsultationCaddieServlet");
					return;
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
					redirect(res, "ConsultationCaddieServlet");
					return;
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
