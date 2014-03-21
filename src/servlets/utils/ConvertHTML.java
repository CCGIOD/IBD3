package servlets.utils;
import java.util.Vector;

import bdd.modeles.Programme;

public class ConvertHTML {

	public static String vectorProgrammeToHTML(Vector<Programme> rs){
		String toReturn = "<TABLE BORDER='1'>";
		toReturn+="<CAPTION>Les prochaines représentations sont :</CAPTION>";
		
		for (int i = 0; i < rs.size(); i++) {
			toReturn+="<font color=\"#FFFFFF\"><TR><TH>"+rs.elementAt(i).getNom()+" </TH><TH> "+rs.elementAt(i).getDate()+"</TR>";
		}
		
		return toReturn+"</TABLE>";
	}
}
