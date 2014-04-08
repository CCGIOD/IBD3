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

/**
 * Cette classe permet gérer un caddie virtuel.
 */
public class CaddieVirtuel {

	/**
	 * La date du dernier ajout.
	 */
	public static Date dateDernierAjout;

	/**
	 * La liste d'élément de type caddie (<=> liste de réservations).
	 */
	public static Vector<Caddie> list = new Vector<Caddie>();	

	/**
	 * Permet de vider le caddie.
	 */
	public static void vider (){
		list = new Vector<Caddie>();
	}

	/**
	 * Permet de mettre à jour la date du caddie.
	 * @param d La date.
	 */
	public static void majDate (Date d){
		dateDernierAjout=d;
	}

	/**
	 * Permet de vérifier si le caddie est valide.
	 * @param exp le nombre de jour de validité.
	 */
	public static void checkDate (int exp){
		java.util.Date dateOjd = new java.util.Date();
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.setTime(dateDernierAjout); 
		calendar.add (Calendar.DATE, exp);

		if (calendar.getTime().after(dateOjd))
			vider();	
	}

	/**
	 * Getter sur la liste.
	 * @return list La liste.
	 */
	public static Vector<Caddie> getList (){
		return list;
	}

	/**
	 * Permet d'ajouter un élément au caddie virtuel.
	 * @param numS Le nom du spectacle.
	 * @param dateRep La date de la représentation.
	 * @param zone La zone où reservé.
	 * @param qt La quantité à reservé.
	 * @throws BDException Une erreur retourné par la BDD.
	 */
	public static void ajouterRep (String numS, String dateRep, String zone, String qt) throws BDException {
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
			list.add(new Caddie(ID, infos[0], dateRep, Integer.parseInt(numS), Integer.parseInt(zone), infos[1], Integer.valueOf(qt))); 
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

	/**
	 * Permet de modifier la quantité de places pour un élément de la liste.
	 * @param id L'idée de la reservation.
	 * @param signe Le type de modification.
	 * @throws ParseException L'erreur sur le Integer.parseInt().
	 */
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
