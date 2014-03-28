package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;
import bdd.exceptions.BDExceptionParamError;
import servlets.base.BaseServlet;
import servlets.utils.ConvertHTML;

import java.io.IOException;

@SuppressWarnings("serial")
public class NouvelleRepresentationServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException	{
		super.doGet(req, res);
		header("Ajouter une nouvelle représentation");
		if (!testConnection()){ footer(); return; }		

		String numS, dateS, heureS;
		numS = req.getParameter("numS");
		dateS = req.getParameter("date");
		heureS = req.getParameter("heure");

		try {
			out.println("<p><i>" + ConvertHTML.vectorSpectacleToHTML(BDRequetes.getSpectables())  + "</i></p>");
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
		}

		boolean error = false ;

		// Cas de l'insertion dans la base de données
		if ((numS != null && dateS != null && heureS != null) && (numS != "" && dateS != "" && heureS != "")){
			String nomSpectacle = null ;
			try {
				nomSpectacle = BDRequetes.insertRepresentation(numS, dateS, heureS);
			} catch(BDExceptionParamError e1){
				out.println("<h1>"+e1.getMessageError()+"</h1>");
				if(e1.getParamsError().contains(1)){
					numS="";
					error = true ;
				}
				if(e1.getParamsError().contains(2)){
					dateS="";
					error = true ;
				}
				if(e1.getParamsError().contains(3)){
					heureS="";
					error = true ;
				}
			} catch (BDException e2) {
				out.println("<h1>"+e2.getMessage()+"</h1>");
				error = true ;
			}

			if(!error){
				out.println("<p><i>Vous venez d'ajouter la représentation suivante :</i></p>");
				out.println("<p><i>Pour le spectacle "+nomSpectacle + " (" + numS + ")" +" à l'horaire " + dateS + " " + heureS +"</i></p>");
				out.println("<p>Ajouter une autre représentation <a href=\"/servlet/NouvelleRepresentationServlet?numS="+numS+"\">à ce spectacle</a> ou <a href=\"/servlet/NouvelleRepresentationServlet\">à un autre spectacle</a></p>");

			}
		}

		// Cas affichage du formule (première fois ou error).
		if (((numS == null || dateS == null || heureS == null) || (numS == "" || dateS == "" || heureS == "")) || error ){

			out.println("Veuillez saisir les informations relatives &agrave; la nouvelle représentation :");
			out.println("<P>");
			out.print("<form action=\"");
			out.print("NouvelleRepresentationServlet\" ");
			out.println("method=POST>");

			// Numero de spectacle.
			out.println("Numéro de spectacle :");
			if(numS != "" &&  numS != null )
				out.println("<input type=text value=\"" + numS + "\" size=20 name=numS>");
			else
				out.println("<input type=text size=20 name=numS>");
			out.println("<br>");

			// Date de la représentation
			out.println("Date de la représentation :");
			if(dateS != "" &&  dateS != null)
				out.println("<input type=text value=\"" + dateS + "\" size=20 name=date>");
			else
				out.println("<input type=text size=20 name=date>");
			out.println("<br>");

			// Heure de début de la représentation
			out.println("Heure de début de la représentation :");
			if(heureS != "" &&  heureS != null)
				out.println("<input type=text value=\"" + heureS + "\" size=20 name=heure>");
			else
				out.println("<input type=text size=20 name=heure>");
			out.println("<br>");
			out.println("<input type=submit>");
			out.println("<br>");

			// Test s'il y avait une erreur.
			if(numS == "" || dateS == "" || heureS == ""){
				out.println("Les informations que vous avez fourni sont incorrect");
			}
			out.println("</form>");	
		}

		footer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		doGet(req, res);
	}

	public String getServletInfo() {
		return "Ajoute une représentation à une date donnée pour un spectacle existant";
	}

	public void footer() throws IOException {
		out.println("<hr><p><a href=\"/admin/admin.html\">Page d'administration</a></p>");
		super.footer();
	}
}
