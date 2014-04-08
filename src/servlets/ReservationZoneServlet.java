package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.accessBD.BDRequetesTest;
import bdd.exceptions.BDException;
import bdd.exceptions.BDExceptionIllegal;
import bdd.modeles.Caddie;
import servlets.base.BaseServlet;
import servlets.caddie.CaddieVirtuel;
import servlets.utils.ConvertHTML;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

/**
 * Servlet qui permet de gérer une réservation dans une zone.
 */
@SuppressWarnings("serial")
public class ReservationZoneServlet extends BaseServlet {

	/**
	 * Variable static d'affichage de confirmation.
	 */
	private static String cad_ok = null;

	/**
	 * Méthode doGet de la Servlet.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
		header("Réservation de places de spectacle");
		if (!testConnection()){ footer(); return; }	

		String nomS, numS, date, zone, nofp, c, work;
		nomS = req.getParameter("nomS");
		numS = req.getParameter("numS");
		date = req.getParameter("date");
		zone = req.getParameter("zone");
		nofp = req.getParameter("nofp");
		work = req.getParameter("work");
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
				out.println("<h2> Pour la représentation du spectacle  : <br>\""+nomS+"\" le "+date+"</h2>");
				out.println("<p><i>"+ConvertHTML.vectorZoneToHTML(BDRequetes.getZones(),numS, nomS, date)+"</i></p>");
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}

		}
		if (numS != null && date != null && c != null && nomS != null && nofp != null && work != null && work.compareTo("R") == 0){
			try {
				String[] infos = BDRequetesTest.testAjoutCaddie(numS, date, zone);
				Caddie tmpCad = new Caddie(1, infos[0], date, Integer.parseInt(numS), Integer.parseInt(zone), infos[1], Integer.valueOf(nofp));
				Vector<Caddie> cads = new Vector<Caddie>();
				cads.add(tmpCad);
				Vector<Caddie> deleteC = null ;
				try {
					deleteC = BDRequetesTest.testCheckValideCaddie(cads, 0);
				} catch (ParseException e1) {
				}
				if(deleteC != null && deleteC.size() > 0){
					out.println("<p><i>" + ConvertHTML.vectorCaddieDeletionToHTML(deleteC) + "</i></p>");
				}
				else{
					try {
						out.println("<p><i>" + ConvertHTML.vectorTicketsToHTML(BDRequetes.valideCaddie(cads)));
					} catch (ParseException e) {
					}
				}

			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}	
		}
		else if (numS != null && date != null && c != null && nomS != null && nofp != null){
			try {
				if (session.getAttribute("config").toString().compareTo("P") == 0)
					BDRequetes.addRepresentationCaddie(numS, date, c, nofp);
				else 
					CaddieVirtuel.ajouterRep(numS, date, c, nofp);
				cad_ok=c;
				redirect(res, "ReservationZoneServlet?numS="+numS+"&date="+date+"&nomS="+nomS);
				return;
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}				
		}

		if (cad_ok != null){
			out.println("<h4> Votre réservation en zone "+cad_ok+" pour la représentation du spectacle \""+nomS+"\" le "+date+" a été ajoutée au caddie ! <i><a href=\"ConsultationCaddieServlet\">(Voir le caddie)</a></i></h4>");
			cad_ok=null;
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
		return "Permet de réserver des places dans une zone pour une représentation d'un spectacle donné";
	}

	/**
	 * Redéfinition de la méthode footer de la Servlet.
	 */
	public void footer() throws IOException {		
		out.println("<hr><p class=\"backlink\"><a href=\"/servlet/ProgrammeServlet\">Retour au programme complet</a></p>");
		super.footer();
	}
}
