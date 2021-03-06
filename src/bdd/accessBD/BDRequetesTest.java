package bdd.accessBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import servlets.caddie.CaddieVirtuel;
import bdd.exceptions.BDException;
import bdd.exceptions.BDExceptionIllegal;
import bdd.modeles.Caddie;

/**
 * Classe permettant de traiter des requêtes avec la base de données.
 * Ces requêtes ont pour but de tester des paramêtrses.
 */
public class BDRequetesTest {

	/**
	 * Teste si un numéro de spectacle existe.
	 * @param conn
	 * 		: Une connection valide à la BD.
	 * @param numS
	 * 		: Le numéro à tester.
	 * @return
	 * 		Le nom du spectacle sinon une erreur.
	 * @throws BDException
	 */
	public static String testNumSpectable (Connection conn, String numS) throws BDException {
		String requete;
		Statement stmt;
		ResultSet rs;
		String res = null ;

		try{ Integer.parseInt(numS); }  
		catch(NumberFormatException nfe){ 
			throw new BDExceptionIllegal("Problème de numéro de représentation (pas un entier)");
		}
		requete = "select numS, nomS from LesSpectacles where numS="+numS;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next())
				throw new BDException("Le spectacle n°"+numS+" n'existe pas");
			else
				res = rs.getString(2);
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des spectacles (Code Oracle : "+e.getErrorCode()+")");
		}
		return res ;
	}

	/**
	 * Teste si une date est valide.
	 * Génère une erreur en cas d'erreur.
	 * @param date
	 * 		: La date à tester.
	 * @throws BDException
	 */
	public static void testDateValide(String date) throws BDException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		try {
			dateFormat.parse(date);
		} catch (Exception e) {
			throw new BDExceptionIllegal("Problème la date n'est pas valide");
		}
	}

	/**
	 * Teste si une heure est valide.
	 * Génère une erreur en cas d'erreur.
	 * @param heure
	 * 		: L'heure à tester.
	 * @throws BDException
	 */
	public static void testHeureValide(String heure) throws BDException {
		SimpleDateFormat heureFormat = new SimpleDateFormat("HH:MM");
		try {
			heureFormat.parse(heure);
		} catch (Exception e) {
			throw new BDExceptionIllegal("Problème l'heure n'est pas valide");
		}
	}

	/**
	 * Teste si une représentation existe.
	 * @param conn
	 * 		: Une connection valide à la BD.
	 * @param numS
	 * 		: Le numéro du spectacle.
	 * @param myDate
	 * 		: La date de la représentation.
	 * @throws BDException
	 */
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

	/**
	 * Teste la validité des paramêtres d'une réservation.
	 * @param nomS
	 * 		: Le nom du spectacle.
	 * @param dateS
	 * 		: La date de la représentation.
	 * @param numS
	 * 		: Le numéro du spectacle.
	 * @throws BDException
	 */
	public static void testParametreReservation (String nomS, String dateS, String numS) throws BDException {
		String requete = "select * from lesspectacles, lesrepresentations where lesspectacles.numS=lesrepresentations.numS and lesspectacles.numS="+numS+" and dateRep = to_date('"+dateS+"','dd/mm/yyyy hh24:mi') and nomS='"+nomS+"'";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next()) {
				throw new BDExceptionIllegal("");
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des spectacles et représentations (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
	}

	/**
	 * Test les paramêtres d'ajout dans le caddie.
	 * @param numS
	 * 		: Le numéro du spectacle.
	 * @param dateRep
	 * 		: La date de la représentation.
	 * @param zone
	 * 		: La zone souhaité pour la réservation.
	 * @return
	 * 		Le nom du spectacle et le nom de la zone.
	 * @throws BDException
	 */
	public static String[] testAjoutCaddie (String numS, String dateRep, String zone) throws BDException {
		String requete = "select nomS from lesspectacles, lesrepresentations where lesspectacles.numS=lesrepresentations.numS and lesspectacles.numS="+numS+" and dateRep = to_date('"+dateRep+"','dd/mm/yyyy hh24:mi')";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		String[] toReturn = new String[2];

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next()) {
				throw new BDException("Problème dans l'interrogation des spectacles, des représentations et des zones");
			}
			else
				toReturn[0]=rs.getString(1);

			requete = "select nomC  from leszones where numZ="+zone;
			rs = stmt.executeQuery(requete);
			if (!rs.next()) {
				throw new BDException("Problème dans l'interrogation des spectacles, des représentations et des zones");
			}
			else
				toReturn[1]=rs.getString(1);

		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des spectacles, des représentations et des zones (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return toReturn;
	}

	/**
	 * Test et insert un nouveau dossier.
	 * @param conn
	 * 		: Une connection valide à la BD.
	 * @param finalPrice
	 * 		: Le prix de la commande.
	 * @return
	 * 		Le numéro du dossier créé.
	 * @throws BDException
	 */
	public static int testNouveauDossier (Connection conn,int finalPrice) throws BDException {
		String requete = "select max(nodossier) from lesdossiers";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int toReturn = 0;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next()) {
				throw new BDException("Problème, impossible de créer un nouveau dossier");
			}
			else
				toReturn=rs.getInt(1);

			requete = "insert INTO lesdossiers values (?,?)";
			pstmt = conn.prepareStatement(requete);
			pstmt.setInt(1, toReturn+1 );
			pstmt.setInt(2, finalPrice);

			int nb_insert = pstmt.executeUpdate();

			if (nb_insert != 1)
				throw new BDException("Problème lors de la création du dossier");


		} catch (SQLException e) {
			throw new BDException("Problème dossier (Code Oracle : "+e.getErrorCode()+")");
		}
		return toReturn+1;
	}

	/**
	 * Retourne le futur numéro de ticket à utiliser.
	 * @param conn
	 * 		: Une connection valide à la BD.
	 * @return
	 * 		Le futur numéro de ticket.
	 * @throws BDException
	 */
	public static int testNouveauTicket (Connection conn) throws BDException {
		String requete = "select max(noserie) from lestickets";
		Statement stmt = null;
		ResultSet rs = null;

		int toReturn = 0;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if (!rs.next()) {
				throw new BDException("Problème, impossible de créer un nouveau ticket");
			}
			else
				toReturn=rs.getInt(1);

			requete = "insert INTO lesdossiers values (?,?)";


		} catch (SQLException e) {
			throw new BDException("Problème ticket (Code Oracle : "+e.getErrorCode()+")");
		}
		return toReturn+1;
	}

	/**
	 * Test si la demande de validation d'un caddie est valide.
	 * @param caddies
	 * 		: Le caddie à tester.
	 * @param formCaddie
	 * 		: 0 si caddie static, 1 si caddie persistant, 2 si caddie volatile.
	 * @return
	 * 		La liste des caddies invalide et donc supprimé du caddie.
	 * @throws BDException
	 */
	public static Vector<Caddie> testCheckValideCaddie(Vector<Caddie> caddies, int formCaddie) throws ParseException, BDException{
		Vector<Caddie> toDelete = new Vector<Caddie>();

		// Vérification
		for(int i = 0 ; i< caddies.size() ; i++){
			Caddie caddie = caddies.get(i);
			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
			long t1 = (new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(caddie.getDate()).getTime() ;
			long t2 = (new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(ft.format(dNow)).getTime();
			if(t1 < t2){
				Caddie delete = new Caddie(caddie.getId(), caddie.getNom(), caddie.getDate(), caddie.getNumS(), caddie.getZone(), caddie.getNomZ(), caddie.getQt());
				toDelete.add(delete);
			}
		}

		// Suppression 
		if(formCaddie > 0)
		{
			for(int i = 0 ; i< toDelete.size() ; i++){
				Caddie caddie = toDelete.get(i);
				if(formCaddie == 1)
					BDRequetes.setQtCaddie(Integer.toString(caddie.getId()), 'd');
				else
					CaddieVirtuel.setQt(Integer.toString(caddie.getId()), 'd');
			}
		}

		return toDelete;
	}
}
