package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.accessBD.BDRequetesTest;
import bdd.exceptions.BDException;
import bdd.exceptions.BDExceptionIllegal;

import servlets.base.BaseServlet;
import servlets.caddie.CaddieVirtuel;
import servlets.utils.ConvertHTML;

import java.io.IOException;

@SuppressWarnings("serial")
public class ReservationZoneServlet extends BaseServlet {

	private static String cad_ok = null;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
		header("Réservation de places de spectacle");
		if (!testConnection()){ footer(); return; }	

		String nomS, numS, date, zone, c;
		nomS = req.getParameter("nomS");
		numS = req.getParameter("numS");
		date = req.getParameter("date");
		zone = req.getParameter("zone");
		c = req.getParameter("c");

		if (numS == null || date == null || nomS == null){
			redirect(res, "ProgrammeServlet");
			return;
		}
		else
			date=date.replaceAll("%20", " ");

		if (zone == null || zone == ""){		
			try {
				BDRequetesTest.testParametreReservation(nomS, date, numS);
			} catch (BDExceptionIllegal e) {
				redirect(res, "ProgrammeServlet");
				return;
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}

			try {
				out.println("<h2> Pour la représentation du spectacle \""+nomS+"\" le "+date+" :</h2>");
				out.println("<p><i>"+ConvertHTML.vectorZoneToHTML(BDRequetes.getZones(),numS, nomS, date)+"</i></p>");
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}

		}

		if (numS != null && date != null && c != null && nomS != null){
			try {
				if (session.getAttribute("config").toString().compareTo("P") == 0)
					BDRequetes.addRepresentationCaddie(numS, date, c);
				else 
					CaddieVirtuel.ajouterRep(numS, date, c);
				cad_ok=c;
				redirect(res, "ReservationZoneServlet?numS="+numS+"&date="+date+"&nomS="+nomS);
				return;
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}				
		}

		if (cad_ok != null){
			out.println("<h4> Une place en zone "+cad_ok+" pour la représentation du spectacle \""+nomS+"\" le "+date+" a été ajoutée au caddie ! <i><a href=\"ConsultationCaddieServlet\">(Voir le caddie)</a></i></h4>");
			cad_ok=null;
		}	

		footer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		doGet(req, res);
	}

	public String getServletInfo() {
		return "Permet de réserver des places dans une zone pour une représentation d'un spectacle donné";
	}

	public void footer() throws IOException {		
		out.println("<hr><p><a href=\"/servlet/ProgrammeServlet\">Retour au programme complet</a></p>");
		super.footer();
	}
}
