package bdd.accessBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BDRequetesTest {

	public static String testNumSpectable (Connection conn, String numS) throws BDException, IllegalArgumentException {
		String requete;
		Statement stmt;
		ResultSet rs;
		String res = null ;

		try{ Integer.parseInt(numS); }  
		catch(NumberFormatException nfe){ 
			throw new IllegalArgumentException("Problème de numéro de représentation (pas un entier)");
		}
		requete = "select numS from LesSpectacles where numS="+numS;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next())
				throw new BDException("Le spectacle n°"+numS+" n'existe pas");
			else
				res = rs.getString(1);
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des spectacles (Code Oracle : "+e.getErrorCode()+")");
		}
		return res ;
	}

	public static void testDateValide(String date) throws IllegalArgumentException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		try {
			dateFormat.parse(date);
		} catch (Exception e) {
			throw new IllegalArgumentException("Problème la date n'est pas valide");
		}
	}

	public static void testHeureValide(String heure) throws IllegalArgumentException {
		SimpleDateFormat heureFormat = new SimpleDateFormat("HH:MM");
		try {
			heureFormat.parse(heure);
		} catch (Exception e) {
			throw new IllegalArgumentException("Problème l'heure n'est pas valide");
		}
	}

	public static void testRepresentation (Connection conn, String numS, String myDate) throws BDException {
		String requete;
		Statement stmt;
		ResultSet rs;

		try{ Integer.parseInt(numS); }  
		catch(NumberFormatException nfe){ 
			throw new BDException("Problème de numéro de représentation (pas un entier)");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf.setLenient(true);

		try { sdf.parse(myDate); } catch (ParseException e) {
			throw new BDException("Problème de date (mauvais format)");
		}

		requete = "select numS from LesSpectacles where numS="+numS;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next())
				throw new BDException("Le spectacle n°"+numS+" n'existe pas");
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des spectacles (Code Oracle : "+e.getErrorCode()+")");
		}

		requete = "select * from LesRepresentations where numS="+numS+" and dateRep = to_date('"+myDate+"','dd/mm/yyyy hh24:mi')";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next())
				throw new BDException("La représentation du spectacle n°"+numS+" à la date "+myDate+" n'existe pas");
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des représentations (Code Oracle : "+e.getErrorCode()+")");
		}
	}
}
