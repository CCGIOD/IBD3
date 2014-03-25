package servlets.utils;
import java.util.Vector;

import bdd.modeles.Representation;
import bdd.modeles.Spectacle;

public class ConvertHTML {

	public static String vectorProgrammeToHTML(Vector<Representation> rs){
		String toReturn = "<TABLE BORDER='1'>";
		toReturn+="<CAPTION>Les prochaines représentations sont :</CAPTION>";
		
		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getNom()+" </TH><TH> "+rs.elementAt(i).getDate()+"</TR>";
		}
		
		return toReturn+"</TABLE>";
	}
	
	public static String vectorSpectacleConsultationToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1'>";
		toReturn+="<CAPTION>Les spectacle sont :</CAPTION>";
		
		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom();
			toReturn+="<TH><a style=\"color:white;\" href=\"/servlet/RepParProgrammeServlet?numS="+rs.elementAt(i).getNum()+"\">VOIR</a></TH></TR>";
		}
		
		return toReturn+"</TABLE>";
	}
	
	// Pour le debuggage.
	public static String vectorSpectacleToHTML(Vector<Spectacle> rs){
		String toReturn = "<TABLE BORDER='1'>";
		toReturn+="<CAPTION>Les spectacle sont :</CAPTION>";
		
		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getNum()+" </TH><TH> "+rs.elementAt(i).getNom()+"</TR>";
		}
		
		return toReturn+"</TABLE>";
	}
}
