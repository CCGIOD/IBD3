package servlets.utils;

import java.util.Vector;

import bdd.modeles.*;

public class ConvertHTML {
	
	public static String vectorProgrammeToHTML(Vector<Representation> rs){
		if (rs.isEmpty())
			return "Il n'y a pas de représentation à venir ...";
		
		String toReturn = "<TABLE BORDER='1' width=\"800\">";
		toReturn+="<CAPTION>Les prochaines représentations sont :</CAPTION>";
		
		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getNom()+" </TH><TH> "+rs.elementAt(i).getDate();
			toReturn+="<TH><a style=\"color:white;\" href=\"/servlet/PlacesRepresentationServlet?numS="+rs.elementAt(i).getNumS()+"&date="+rs.elementAt(i).getDate()+"\">VOIR PLACES DISPONIBLES</a></TH></TR>";
		}
		
		return toReturn+"</TABLE>";
	}
	
	public static String vectorPlaceToHTML(Vector<Place> rs){
		if (rs.isEmpty())
			return "Il n'y a plus de places disponibles.";
		
		String toReturn = "<TABLE BORDER='1' width=\"300\">";
		toReturn+="<CAPTION>Les places disponibles (#) sont :</CAPTION>";
		
		int i;
		for (i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getRang()+" </TH><TH> "+rs.elementAt(i).getNum()+"</TR>";
		}
		toReturn=toReturn.replaceAll("#", i+"");
		
		return toReturn+"</TABLE>";
	}
	
	//select * from LesPlaces WHERE LesPlaces.NOPLACE NOT IN (select NOPLACE from LesTickets where numS = 101);
	//insert into LESREPRESENTATIONS values ('104', to_date('25/01/2009 20:30','dd/mm/yyyy hh24:mi'));
	
	public static String vectorSpectacleConsultationToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1' width=\"600\">";
		toReturn+="<CAPTION>Les spectacle sont :</CAPTION>";
		
		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom();
			toReturn+="<TH><a style=\"color:white;\" href=\"/servlet/RepParProgrammeServlet?numS="+rs.elementAt(i).getNum()+"\">VOIR</a></TH></TR>";
		}
		
		return toReturn+"</TABLE>";
	}
	
	// Pour le debuggage.
	public static String vectorSpectacleToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1' width=\"400\">";
		toReturn+="<CAPTION>Les spectacle sont :</CAPTION>";
		
		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom()+"</TR>";
		}
		
		return toReturn+"</TABLE>";
	}
}
