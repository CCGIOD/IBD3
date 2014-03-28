package servlets.utils;

import java.util.Vector;

import bdd.modeles.*;

public class ConvertHTML {

	public static String vectorProgrammeToHTML(Vector<Representation> rs, boolean un_seul_spec){
		if (rs.isEmpty())
			return "Il n'y a pas de représentation à venir ...";

		String toReturn = "<TABLE BORDER='1' width=\"1200\">";

		if (!un_seul_spec)
			toReturn+="<CAPTION>Les prochaines représentations sont :</CAPTION>";
		else
			toReturn+="<CAPTION>Les prochaines représentations du spectacle \""+rs.elementAt(0).getNom()+"\" sont :</CAPTION>";

		String previous = "";
		int k=1;
		for (int i = 0; i < rs.size(); i++) {
			if (rs.elementAt(i).getNom().compareTo(previous) != 0){
				toReturn=toReturn.replaceAll("#", k+"");
				k=1;
				previous=rs.elementAt(i).getNom();
				if (!un_seul_spec)
					toReturn+="<TR><TH rowspan=\"#\">"+rs.elementAt(i).getNom()+"</TH><TH rowspan=\"#\"><a href=\"/servlet/RepParProgrammeServlet?numS="+rs.elementAt(i).getNumS()+"\">REPRESENTATIONS DE CE SPECTACLE</a></TH><TH> "+rs.elementAt(i).getDate()+"</TH>";
				else
					toReturn+="<TR><TH rowspan=\"#\">"+rs.elementAt(i).getNom()+"</TH><TH> "+rs.elementAt(i).getDate()+"</TH>";
			}
			else{
				toReturn+="<TR><TH> "+rs.elementAt(i).getDate()+"</TH>";
				k++;
			}

			toReturn+="<TH><a href=\"/servlet/PlacesRepresentationServlet?numS="+rs.elementAt(i).getNumS()+"&date="+rs.elementAt(i).getDate()+"\">VOIR LES PLACES DISPONIBLES</a></TH>";
			toReturn+="<TH><a href=\"/servlet/ReservationZoneServlet?numS="+rs.elementAt(i).getNumS()+"&date="+rs.elementAt(i).getDate()+"\">RESERVEZ DES PLACES</a></TH></TR>";
		}
		toReturn=toReturn.replaceAll("#", k+"");

		return toReturn+"</TABLE>";
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
					toReturn+="<TH>"+rs.elementAt(i).getNum()+"</TH>";
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
			toReturn+="<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom();
			toReturn+="<TH><a href=\"/servlet/RepParProgrammeServlet?numS="+rs.elementAt(i).getNum()+"\">VOIR</a></TH></TR>";
		}

		return toReturn+"</TABLE>";
	}

	public static String vectorZoneToHTML(Vector<Zone> rs){
		String toReturn = "<TABLE BORDER='1' width=\"600\">";
		toReturn+="<CAPTION>Les zones sont :</CAPTION>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNomC();
		}

		return toReturn+"</TABLE>";
	}

	public static String vectorSpectacleToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1' width=\"400\">";
		toReturn+="<CAPTION>Les spectacles sont :</CAPTION>";

		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom()+"</TR>";
		}

		return toReturn+"</TABLE>";
	}
}
