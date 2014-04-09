package jsp;

import java.util.Vector;

import bdd.modeles.*;

/**
 * Cette classe permet de fournir du code HTML à partir de données venant de la BDD stockées dans un modèle.
 * Les méthodes de cette classe sont statiques et sont destinées à être appelées depuis une JSP.
 */
public class ConvertHTML {

	/**
	 * Donne le tableau HTML affichant le programme.
	 * @param rs Le vector de représentation.
	 * @param un_seul_spec Indique s'il s'agit d'un programme ou d'une liste de représentation d'un seul spectacle.
	 * @return String du tableau HTML.
	 */
	public static String vectorProgrammeToHTML(Vector<Representation> rs, boolean un_seul_spec){
		if (rs.isEmpty())
			return "<p class=\"plusbas\">Il n'y a pas de représentation à venir ...</p>";

		String toReturn = "<TABLE BORDER='1' width=\"1000\">\n";

		if (!un_seul_spec)
			toReturn+="<CAPTION>Les prochaines représentations sont :</CAPTION>\n";
		else
			toReturn+="<CAPTION>Les prochaines représentations du spectacle \""+rs.elementAt(0).getNom()+"\" sont :</CAPTION>\n";

		String previous = "";
		int k=1;
		for (int i = 0; i < rs.size(); i++) {
			if (rs.elementAt(i).getNom().compareTo(previous) != 0){
				toReturn=toReturn.replaceAll("#", k+"");
				k=1;
				previous=rs.elementAt(i).getNom();
				if (!un_seul_spec)
					toReturn+="<TR>\n<TH rowspan=\"#\">"+rs.elementAt(i).getNom()+"</TH>\n<TH rowspan=\"#\"><a href=\"liste_rep.jsp?numS="+rs.elementAt(i).getNumS()+"\">REPRESENTATIONS</a></TH>\n<TH> "+rs.elementAt(i).getDate()+"</TH>\n";
				else
					toReturn+="<TR>\n<TH rowspan=\"#\">"+rs.elementAt(i).getNom()+"</TH>\n<TH> "+rs.elementAt(i).getDate()+"</TH>\n";
			}
			else{
				toReturn+="<TR>\n<TH> "+rs.elementAt(i).getDate()+"</TH>\n";
				k++;
			}

			toReturn+="<TH><a href=\"place.jsp?numS="+rs.elementAt(i).getNumS()+"&date="+rs.elementAt(i).getDate()+"&nomS="+rs.elementAt(i).getNom()+"\">PLACES DISPONIBLES</a></TH>\n";
			toReturn+="<TH><a href=\"reservation.jsp?numS="+rs.elementAt(i).getNumS()+"&date="+rs.elementAt(i).getDate()+"&nomS="+rs.elementAt(i).getNom()+"\">RESERVER DES PLACES</a></TH>\n</TR>\n";
		}
		toReturn=toReturn.replaceAll("#", k+"");

		return toReturn+"</TABLE>\n";
	}

	/**
	 * Donne le tableau HTML affichant les places disponibles.
	 * @param rs Le vector de places.
	 * @return String du tableau HTML.
	 */
	public static String vectorPlaceToHTML(Vector<Place> rs){
		if (rs.isEmpty())
			return "<p class=\"plusbas\">Il n'y a plus de places disponibles.</p>";

		String toReturn = "<TABLE BORDER='1' width=\"1000\">";
		toReturn+="<CAPTION>Les places disponibles (#) sont :</CAPTION>";

		int i=0;
		for (int j=1;j<=10;j++){
			toReturn+="<TR><TH class=\"c0\"> Rang n°"+j+" </TH>";
			for (int k=1;k<=30;k++){
				if (i < rs.size() && rs.elementAt(i).getRang() == j && rs.elementAt(i).getNum() == k){
					toReturn+="<TH class=\"c"+rs.elementAt(i).getZone()+"\">"+rs.elementAt(i).getNum()+"</TH>";
					i++;
				}
				else
					toReturn+="<TH></TH>";
			}
			toReturn+="</TR>";
		}
		toReturn=toReturn.replaceAll("#", i+"");

		return toReturn+"</TABLE>";
	}

	/**
	 * Donne la liste des spectacles sans intéractions VOIR possible en HTML.
	 * @param rs Le vector de spectacles.
	 * @return String du tableau HTML.
	 */
	public static String vectorSpectacleConsultationToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1' width=\"600\">";
		toReturn+="<CAPTION>Les spectacles sont :</CAPTION>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom()+"</TH>";
			toReturn+="<TH><a href=\"liste_rep.jsp?numS="+rs.elementAt(i).getNum()+"\">VOIR</a></TH></TR>";
		}

		return toReturn+"</TABLE>";
	}

	/**
	 * Donne le caddie sous forme HTML.
	 * @param rs Le vector de caddie.
	 * @return String du tableau HTML.
	 */
	public static String vectorCaddieToHTML(Vector<Caddie> rs){
		if (rs.isEmpty())
			return "<p class=\"plusbas\">Le caddie est vide.</p>";

		String toReturn = "<TABLE BORDER='1' width=\"1000\">";
		toReturn+="<CAPTION>Le caddie :</CAPTION>";

		toReturn+="<TR>";
		toReturn+="<TH> <i>Reservation n°</i></TH>";			
		toReturn+="<TH> <i>Spectacle</i> </TH><TH> <i>Date représentation</i></TH>";
		toReturn+="<TH> <i> Zone </i></TH>";			
		toReturn+="<TH> <i>Nombre de places (Ajouter/Retirer)</i></TH>";		
		toReturn+="<TH> <i>Supprimer la réservation</i></TH>";			
		toReturn+="</TR>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR>";
			toReturn+="<TH> "+(i+1)+"</TH>";			
			toReturn+="<TH>"+rs.elementAt(i).getNom()+" </TH><TH> "+rs.elementAt(i).getDate()+"</TH>";
			toReturn+="<TH> "+rs.elementAt(i).getZone()+" ("+rs.elementAt(i).getNomZ()+")</TH>";			
			if (rs.elementAt(i).getQt() > 1)
				toReturn+="<TH> <a href=\"caddie.jsp?idm="+rs.elementAt(i).getId()+"\" >(-)</a> "+rs.elementAt(i).getQt()+" <a href=\"caddie.jsp?idp="+rs.elementAt(i).getId()+"\" >(+)</a></TH>";			
			else
				toReturn+="<TH>"+rs.elementAt(i).getQt()+" <a href=\"caddie.jsp?idp="+rs.elementAt(i).getId()+"\" >(+)</a></TH>";			
			toReturn+="<TH> <a href=\"caddie.jsp?idd="+rs.elementAt(i).getId()+"\" >(X)</a> </TH>";			
			toReturn+="</TR>";
		}

		return toReturn+"</TABLE><form action=\"\"><input type=\"submit\" name=\"valider\" value=\"Valider le caddie\"/></form>";
	}

	/**
	 * Donne la liste des tickets après une réservation sous forme HTML.
	 * @param rs Le vector de tickets.
	 * @return String du tableau HTML.
	 */
	public static String vectorTicketsToHTML(Vector<Ticket> rs){

		int totalCommand = 0 ;

		String toReturnHead = "<TABLE BORDER='1' width=\"1000\">";
		toReturnHead+="<CAPTION>La commande (prix : ";

		String toReturn = ") :</CAPTION>";
		toReturn+="<TR>";
		toReturn+="<TH> <i>Dossier n°</i></TH>";			
		toReturn+="<TH> <i>Ticket n°</i></TH>";	
		toReturn+="<TH> <i>Place n°</i></TH>";
		toReturn+="<TH> <i>Spectacle</i> </TH>";
		toReturn+="<TH> <i>Date représentation</i></TH>";
		toReturn+="<TH> <i>Zone</i></TH>";			
		toReturn+="<TH> <i>Rang</i></TH>";			
		toReturn+="<TH> <i>Prix</i></TH>";		
		toReturn+="<TH> <i>Date Emission</i></TH>";			
		toReturn+="</TR>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR>";
			toReturn+="<TH>"+rs.get(i).getNumDossier()  +"</TH>";			
			toReturn+="<TH>"+rs.get(i).getNumTicket()  +"</TH>";	
			toReturn+="<TH>"+rs.get(i).getNumPlace()  +"</TH>";
			toReturn+="<TH>"+rs.get(i).getNomS()  +"</TH>";
			toReturn+="<TH>"+rs.get(i).getDateRep()  +"</TH>";
			toReturn+="<TH>"+rs.get(i).getZonePlace()  +"</TH>";
			toReturn+="<TH>"+rs.get(i).getRangPlace()  +"</TH>";			
			toReturn+="<TH>"+rs.get(i).getPrix()  +"</TH>";		
			toReturn+="<TH>"+rs.get(i).getDateEmission()  +"</TH>";			
			toReturn+="</TR>";
			totalCommand+=rs.get(i).getPrix();
		}

		return toReturnHead + totalCommand + toReturn+"</TABLE>";
	}

	/**
	 * Donne la liste des places et les intéractions associées pour une représentation en HTML.
	 * @param rs Le vector de zone.
	 * @param numS Le nom du spectacle.
	 * @param nomS Le numéro du spectacle.
	 * @param DateRep La date de la représentation.
	 * @return String du tableau HTML.
	 */
	public static String vectorZoneToHTML(Vector<Zone> rs, String numS, String nomS, String DateRep){
		String toReturn = "<TABLE BORDER='1' width=\"800\">";
		toReturn+="<CAPTION>Les zones sont :</CAPTION>";
		toReturn+="<TR>";
		toReturn+="<TH> <i>Numéro de zone</i></TH>";			
		toReturn+="<TH> <i>Type</i> </TH>";
		toReturn+="<TH> <i>Nombre à réserver</i></TH>";
		toReturn+="<TH> <i>Réservation directe</i></TH>";			
		toReturn+="<TH> <i>Ajouter au caddie</i></TH>";		
		toReturn+="</TR>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="\n<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNomC()+"</TH>";
			toReturn+="\n<TH>"
					+ "\n <input type=\"button\" onclick=\"f('" + i + "',-1,'" + "reservation.jsp?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"&zone="+rs.elementAt(i).getNum() + "')\" value=\"-\">"
					+ "\n <span id=\"nbofp" + i + "\">1</span>"
					+ "\n <input type=\"button\" onclick=\"f('" + i + "',1,'" + "reservation.jsp?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"&zone="+rs.elementAt(i).getNum() + "')\" value=\"+\">"
					+	"\n</TH>" ;
			toReturn+="\n<TH><a id=\"resZone" + i + "\"href=\"reservation.jsp?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"&zone="+rs.elementAt(i).getNum()+ "&nofp=1&work=R" + "\">RESERVER</a></TH><TH><a id=\"caddie" + i + "\"href=\"reservation.jsp?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"&nofp=1\">AJOUTER AU CADDIE</a></TH></TR>";
		}

		String scriptJs = "<script>"
				+ "function f(id,type,link)"
				+ "{"
				+ "var obj = document.getElementById('nbofp'+id);"
				+ "if(type == 1)"
				+ "obj.innerHTML=parseInt(obj.innerHTML)+1;"
				+ "else if(obj.innerHTML > 1)"
				+ "obj.innerHTML=parseInt(obj.innerHTML)-1;"
				+ "document.getElementById('resZone'+id).href=link + '&work=R' + '&nofp=' + obj.innerHTML ;"
				+ "document.getElementById('caddie'+id).href=link + '&nofp=' + obj.innerHTML ;"
				+ "}"
				+ "</script>";

		return toReturn+"</TABLE>" + scriptJs ;
	}

	/**
	 * Donne la liste des spectacles avec intéractions VOIR possible en HTML.
	 * @param rs Le vector de spectacles.
	 * @return String du tableau HTML.
	 */
	public static String vectorSpectacleToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1' width=\"400\">";
		toReturn+="<CAPTION>Les spectacles sont :</CAPTION>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom()+"</TH></TR>";
		}

		return toReturn+"</TABLE>";
	}

	/**
	 * Donne la liste des reservations impossible en en HTML.
	 * @param rs Le vector de caddie.
	 * @return String du tableau HTML.
	 */
	public static String vectorCaddieDeletionToHTML(Vector<Caddie> rs){
		if (rs.isEmpty())
			return "";

		String toReturn = "<TABLE BORDER='1' width=\"1000\">";
		toReturn+="<CAPTION>Ces places ne sont pas réservables car la représentation a déjà eu lieu :</CAPTION>";

		toReturn+="<TR>";
		toReturn+="<TH> <i>Reservation n°</i></TH>";			
		toReturn+="<TH> <i>Spectacle</i> </TH><TH> <i>Date représentation</i></TH>";
		toReturn+="<TH> <i> Zone </i></TH>";						
		toReturn+="</TR>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR>";
			toReturn+="<TH> "+(i+1)+"</TH>";			
			toReturn+="<TH>"+rs.elementAt(i).getNom()+" </TH><TH> "+rs.elementAt(i).getDate()+"</TH>";
			toReturn+="<TH> "+rs.elementAt(i).getZone()+" ("+rs.elementAt(i).getNomZ()+")</TH>";			
			toReturn+="</TR>";
		}

		return toReturn+"</TABLE>";
	}
}
