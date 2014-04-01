package servlets.caddie;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

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
		// Tester si l'insertion est possible 
	}
	
	public static void setQt (String id, char signe) throws ParseException {
		// Mettre à jour la qt selon le signe
	}
}
