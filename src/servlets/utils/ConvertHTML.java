package servlets.utils;

import java.util.Vector;

import bdd.modeles.*;

public class ConvertHTML {

	public static String vectorProgrammeToHTML(Vector<Representation> rs, boolean un_seul_spec){
		if (rs.isEmpty())
			return "<p>Il n'y a pas de représentation à venir ...</p>";

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
					toReturn+="<TR>\n<TH rowspan=\"#\">"+rs.elementAt(i).getNom()+"</TH>\n<TH rowspan=\"#\"><a href=\"/servlet/RepParProgrammeServlet?numS="+rs.elementAt(i).getNumS()+"\">REPRESENTATIONS</a></TH>\n<TH> "+rs.elementAt(i).getDate()+"</TH>\n";
				else
					toReturn+="<TR>\n<TH rowspan=\"#\">"+rs.elementAt(i).getNom()+"</TH>\n<TH> "+rs.elementAt(i).getDate()+"</TH>\n";
			}
			else{
				toReturn+="<TR>\n<TH> "+rs.elementAt(i).getDate()+"</TH>\n";
				k++;
			}

			toReturn+="<TH><a href=\"/servlet/PlacesRepresentationServlet?numS="+rs.elementAt(i).getNumS()+"&date="+rs.elementAt(i).getDate()+"\">PLACES DISPONIBLES</a></TH>\n";
			toReturn+="<TH><a href=\"/servlet/ReservationZoneServlet?numS="+rs.elementAt(i).getNumS()+"&date="+rs.elementAt(i).getDate()+"&nomS="+rs.elementAt(i).getNom()+"\">RESERVER DES PLACES</a></TH>\n</TR>\n";
		}
		toReturn=toReturn.replaceAll("#", k+"");

		return toReturn+"</TABLE>\n";
	}

	public static String vectorPlaceToHTML(Vector<Place> rs){
		if (rs.isEmpty())
			return "Il n'y a plus de places disponibles.";

		String toReturn = "<TABLE BORDER='1' width=\"1000\">";
		toReturn+="<CAPTION>Les places disponibles (#) sont :</CAPTION>";

		int i=0;
		for (int j=1;j<=10;j++){
			toReturn+="<TR><TH> Rang n°"+j+" </TH>";
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

	public static String vectorSpectacleConsultationToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1' width=\"600\">";
		toReturn+="<CAPTION>Les spectacles sont :</CAPTION>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom()+"</TH>";
			toReturn+="<TH><a href=\"/servlet/RepParProgrammeServlet?numS="+rs.elementAt(i).getNum()+"\">VOIR</a></TH></TR>";
		}

		return toReturn+"</TABLE>";
	}
	
	public static String vectorCaddieToHTML(Vector<Caddie> rs){
		if (rs.isEmpty())
			return "Le caddie est vide.";
		
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
				toReturn+="<TH> <a href=\"/servlet/ConsultationCaddieServlet?idm="+rs.elementAt(i).getId()+"\" >(-)</a> "+rs.elementAt(i).getQt()+" <a href=\"/servlet/ConsultationCaddieServlet?idp="+rs.elementAt(i).getId()+"\" >(+)</a></TH>";			
			else
				toReturn+="<TH>"+rs.elementAt(i).getQt()+" <a href=\"/servlet/ConsultationCaddieServlet?idp="+rs.elementAt(i).getId()+"\" >(+)</a></TH>";			
			toReturn+="<TH> <a href=\"/servlet/ConsultationCaddieServlet?idd="+rs.elementAt(i).getId()+"\" >(X)</a> </TH>";			
			toReturn+="</TR>";
		}

		return toReturn+"</TABLE>";
	}

	public static String vectorZoneToHTML(Vector<Zone> rs, String numS, String nomS, String DateRep){
		String toReturn = "<TABLE BORDER='1' width=\"600\">";
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
		    + "\n <input type=\"button\" onclick=\"f('" + i + "',-1,'" + "/servlet/ReservationZoneServlet?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"&zone="+rs.elementAt(i).getNum()+ "&nofp=0" + "')\" value=\"-\">"
		    + "\n <span id=\"nbofp" + i + "\">0</span>"
		    + "\n <input type=\"button\" onclick=\"f('" + i + "',1,'" + "/servlet/ReservationZoneServlet?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"&zone="+rs.elementAt(i).getNum()+ "&nofp=0" + "')\" value=\"+\">"
		    +	"\n</TH>" ;
			toReturn+="\n<TH><a id=\"resZone" + i + "\"href=\"/servlet/ReservationZoneServlet?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"&zone="+rs.elementAt(i).getNum()+ "&nofp=0" + "\">RESERVER</a></TH><TH><a href=\"/servlet/ReservationZoneServlet?numS="+numS+"&date="+DateRep+"&nomS="+nomS+"&c="+(i+1)+"\">AJOUTER AU CADDIE</a></TH></TR>";
		}
		
		String scriptJs = "<script>"
				+ "function f(id,type,link)"
				+ "{"
				+ "var obj = document.getElementById('nbofp'+id);"
				+ "if(type == 1)"
				+ "obj.innerHTML=parseInt(obj.innerHTML)+1;"
				+ "else if(obj.innerHTML > 0)"
				+ "obj.innerHTML=parseInt(obj.innerHTML)-1;"
				+ "document.getElementById('resZone'+id).href=link + '&nofp=' + obj.innerHTML ;"
				+ "}"
				+ "</script>";

		return toReturn+"</TABLE>" + scriptJs ;
	}

	public static String vectorSpectacleToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1' width=\"400\">";
		toReturn+="<CAPTION>Les spectacles sont :</CAPTION>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom()+"</TH></TR>";
		}

		return toReturn+"</TABLE>";
	}
}
