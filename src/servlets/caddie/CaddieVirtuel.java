package servlets.caddie;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import bdd.accessBD.BDRequetesTest;
import bdd.exceptions.BDException;
import bdd.modeles.Caddie;

public class CaddieVirtuel {

	public static Date dateDernierAjout;
	public static Vector<Caddie> list = new Vector<Caddie>();	

	public static void vider (){
		list = new Vector<Caddie>();
	}

	public static void majDate (Date d){
		dateDernierAjout=d;
	}

	public static void checkDate (int exp){
		java.util.Date dateOjd = new java.util.Date();
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.setTime(dateDernierAjout); 
		calendar.add (Calendar.DATE, exp);

		if (calendar.getTime().after(dateOjd))
			vider();	
	}

	public static Vector<Caddie> getList (){
		return list;
	}

	public static void ajouterRep (String numS, String dateRep, String zone) throws BDException {
		String[] infos = BDRequetesTest.testAjoutCaddie(numS, dateRep, zone);

		boolean incr = false;
		int ID = 1;
		for (int i=0;i<list.size();i++){
			if ((list.get(i).getNumS()+"").compareTo(numS) == 0
					&& list.get(i).getDate().compareTo(dateRep) == 0
					&& (list.get(i).getZone()+"").compareTo(zone) == 0){
				list.get(i).setQt(list.get(i).getQt()+1);
				incr=true;
			}
			if (list.get(i).getId() > ID)
				ID=list.get(i).getId()+1;
		}
		if (!incr){
			list.add(new Caddie(ID, infos[0], dateRep, Integer.parseInt(numS), Integer.parseInt(zone), infos[1], 1)); 
		}

		Collections.sort(list,new Comparator<Caddie>() {
			public int compare(Caddie o1, Caddie o2) {
				if (o1.getId() < o2.getId())
					return -1;
				else if (o1.getId() > o2.getId())
					return 1;
				else
					return 0;
			}
		});
	}

	public static void setQt (String id, char signe) throws ParseException {
		for (int i=0;i<list.size();i++){
			if (list.get(i).getId() == Integer.parseInt(id)){
				if (signe == 'd')
					list.remove(i);
				else if (signe == '+')
					list.get(i).setQt(list.get(i).getQt()+1);
				else
					list.get(i).setQt(list.get(i).getQt()-1);
			}
		}
	}
}
