package bdd.accessBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import bdd.exceptions.BDException;
import bdd.exceptions.BDExceptionIllegal;
import bdd.exceptions.BDExceptionParamError;
import bdd.modeles.Caddie;
import bdd.modeles.Place;
import bdd.modeles.Representation;
import bdd.modeles.Spectacle;
import bdd.modeles.Ticket;
import bdd.modeles.Zone;

public class BDRequetes {

	public static Vector<Representation> getRepresentations (String numS) throws BDException {
		Vector<Representation> res = new Vector<Representation>();
		String requete = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			if (numS == null)
				requete = "select nomS, TO_CHAR(DATEREP, 'DD/MM/YYYY HH24:MI') AS DATEREP, LesRepresentations.numS from LesRepresentations, LesSpectacles where LesRepresentations.numS = LesSpectacles.numS";
			else{
				BDRequetesTest.testNumSpectable(conn, numS);
				requete = "select nomS, TO_CHAR(DATEREP, 'DD/MM/YYYY HH24:MI') AS DATEREP, LesRepresentations.numS from LesRepresentations, LesSpectacles where LesRepresentations.numS = LesSpectacles.numS and LesSpectacles.numS="+numS;
			}			

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Representation (rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des représentations (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}

	public static Vector<Representation> getRepresentations () throws BDException {
		return getRepresentations(null);
	}

	public static Vector<Spectacle> getSpectables () throws BDException {
		Vector<Spectacle> res = new Vector<Spectacle>();
		String requete = "select DISTINCT numS, nomS from LesSpectacles";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Spectacle(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des spectacles (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}

	public static Vector<Caddie> getContenuCaddie () throws BDException {
		Vector<Caddie> res = new Vector<Caddie>();
		String requete = "select idr, nomS, TO_CHAR(DATEREP, 'DD/MM/YYYY HH24:MI') AS DATEREP, lecaddie.numS, lecaddie.numZ, nomC, qt from lesspectacles, lecaddie, leszones where lesspectacles.numS=lecaddie.nums and lecaddie.numz = leszones.numz order by idr asc";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Caddie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6) , rs.getInt(7)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation du caddie (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}

	public static void setQtCaddie (String idr, char signe) throws BDException {
		String requete = null;
		if (signe != 'd')
			requete = "update lecaddie set qt = (select qt from lecaddie where idr="+idr+")"+signe+"1 where idr="+idr;
		else
			requete = "delete from lecaddie where idr="+idr;

		Statement stmt = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			int nb_insert = stmt.executeUpdate(requete);

			if(nb_insert != 1)
				throw new BDException("Problème dans la mise à jour d'une quantité du caddie");

			conn.commit();

		} catch (SQLException e) {
			throw new BDException("Problème dans la mise à jour d'une quantité du caddie (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, null);
		}
	}

	public static Vector<Zone> getZones () throws BDException {
		Vector<Zone> res = new Vector<Zone>();
		String requete = "select * from LesZones";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Zone(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des zones (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}

	public static Vector<Place> getPlacesDisponibles (String numS, String date) throws BDException {
		Vector<Place> res = new Vector<Place>();
		String requete = "select norang, noplace, numz from lesplaces MINUS select lestickets.noplace, lestickets.norang, numz from LesTickets, lesplaces where lestickets.noplace = lesplaces.noplace and lestickets.norang=lesplaces.norang and numS = "+numS+" and dateRep = to_date('"+date+"','dd/mm/yyyy hh24:mi') ORDER BY norang, noplace";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn = BDConnexion.getConnexion();

			BDRequetesTest.testRepresentation(conn, numS, date);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Place(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des places (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}
	
	public static Vector<Place> getPlacesDisponiblesFromZone (String numS, String date, int zone) throws BDException {
		Vector<Place> res = new Vector<Place>();
		String requete = "select norang, noplace, numZ from lesplaces where numz=" + zone + " MINUS select lestickets.noplace, lestickets.norang, numz from LesTickets, lesplaces where lestickets.noplace = lesplaces.noplace and lestickets.norang=lesplaces.norang and numS = "+numS+" and dateRep = to_date('"+date+"','dd/mm/yyyy hh24:mi') ORDER BY norang, noplace";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn = BDConnexion.getConnexion();

			BDRequetesTest.testRepresentation(conn, numS, date);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Place(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des places (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}

	public static void addRepresentationCaddie (String numS, String dateRep, String zone, String nofp) throws BDException {
		String requete = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			BDRequetesTest.testRepresentation(conn, numS, dateRep);

			stmt = conn.createStatement();
			rs =stmt.executeQuery("select idr from lecaddie where numS="+numS+" and dateRep = to_date('"+dateRep+"','dd/mm/yyyy hh24:mi') and numZ="+zone);
			if (rs.next()){
				setQtCaddie(rs.getInt(1)+"", '+');
				return;
			}	

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(idr) from lecaddie");		

			int id;
			if (!rs.next())
				id=1;
			else
				id=rs.getInt(1)+1;			

			requete = "insert into LeCaddie values (?,?,?,?,?)";
			pstmt = conn.prepareStatement(requete);
			pstmt.setInt(1, id);
			pstmt.setInt(2, Integer.valueOf(numS));
			pstmt.setTimestamp(3, new Timestamp((new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(dateRep).getTime()));
			pstmt.setInt(4, Integer.valueOf(zone));
			pstmt.setInt(5, Integer.valueOf(nofp));			
			int nb_insert = pstmt.executeUpdate();
			conn.commit();

			if(nb_insert != 1)
				throw new BDException("Problème dans l'ajout d'une représentation dans le caddie");

			stmt.executeUpdate("update config set date_cad = CURRENT_DATE");

		} catch (SQLException e) {
			throw new BDException("Problème dans l'ajout d'une représentation dans le caddie (Code Oracle : "+e.getErrorCode()+")");
		} catch (ParseException e) {}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
			BDConnexion.FermerTout(null, pstmt, null);
		}
	}

	public static String insertRepresentation(String numS, String dateRep, String heureRep) throws BDException, BDExceptionParamError {
		String requete = null;
		PreparedStatement stmt = null;
		Connection conn = null;

		BDExceptionParamError errors = new BDExceptionParamError() ;
		String nomSpectacle = null ;

		try {			
			conn = BDConnexion.getConnexion();

			try {
				nomSpectacle = BDRequetesTest.testNumSpectable(conn,numS);
			} catch (BDExceptionIllegal e) { errors.addError(1, e.getMessage()); }

			try {
				BDRequetesTest.testDateValide(dateRep);
			} catch (BDExceptionIllegal e) { errors.addError(2, e.getMessage()); }

			try {
				BDRequetesTest.testHeureValide(heureRep);
			} catch (BDExceptionIllegal e) { errors.addError(3, e.getMessage()); }


			if(errors.getParamsError().size() != 0){
				throw errors;
			}

			requete = "insert into LESREPRESENTATIONS values (?, ?)";
			stmt = conn.prepareStatement(requete);
			stmt.setInt(1, Integer.valueOf(numS));
			stmt.setTimestamp(2, new Timestamp((new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(dateRep+" "+heureRep).getTime()));
			int nb_insert = stmt.executeUpdate();

			if(nb_insert != 1)
				throw new BDException("Problème, impossible d'insérer une nouvelle représentation");

			conn.commit();

		} catch (SQLException e) {
			throw new BDException("Problème, impossible d'insérer une nouvelle représentation");
		} catch (ParseException e) {}
		finally {
			BDConnexion.FermerTout(conn, stmt, null);
		}

		return nomSpectacle;
	}

	public static void checkCaddieLifetime () throws BDException {
		String requete = "select duree_vie_cad from config";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);

			boolean delete = false;
			if (rs.next() && rs.getInt(1) == -1){
				delete = true;
			}
			else if (rs.getInt(1) > 0){
				rs = stmt.executeQuery("select count(*) from config where date_cad + interval '"+rs.getInt(1)+"' day > CURRENT_DATE");
				if (rs.next() && rs.getInt(1) == 0){
					delete = true;
				}
			}

			if (delete)
				stmt.executeUpdate("delete from lecaddie");

		} catch (SQLException e) {
			throw new BDException("Problème lors de la vérification initiale du caddie (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
	}

	public static int getCaddieLifetime () throws BDException {
		String requete = "select duree_vie_cad from config";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int toReturn = 0;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);

			if (rs.next()){
				toReturn = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new BDException("Problème lors de la récupération de la durée de vie du caddie (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return toReturn;
	}

	public static char getTypeCaddie () throws BDException {
		String requete = "select type_cad from config";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		char toReturn = '?';

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);

			if (rs.next()){
				toReturn = rs.getString(1).charAt(0);
			}

		} catch (SQLException e) {
			throw new BDException("Problème lors de la récupération du type du caddie (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return toReturn;
	}

	public static void majCaddieLifetime (String val) throws BDException {
		String requete = "update config set duree_vie_cad =";
		if (val.compareTo("SESSION") == 0)
			requete+="-1";
		else if (val.compareTo("NOLIMIT") == 0)
			requete+="-2";
		else
			requete+=val;

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			stmt.executeUpdate(requete);			

		} catch (SQLException e) {
			throw new BDException("Problème lors de la mise à jour de la durée de vie du caddie (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
	}

	public static void majTypeCaddie (String val, Vector<Caddie> toInsert) throws BDException {
		String requete = "update config set type_cad =";
		if (val.compareTo("PERSISTANT") == 0)
			requete+="'P'";
		else if (val.compareTo("VOLATILE") == 0)
			requete+="'V'";
		else
			requete+=val;

		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			stmt.executeUpdate(requete);

			if (toInsert != null){
				stmt.executeUpdate("delete from lecaddie");
				for (int i=0;i<toInsert.size();i++){
					requete = "insert into LeCaddie values (?,?,?,?,?)";
					pstmt = conn.prepareStatement(requete);
					pstmt.setInt(1, toInsert.get(i).getId());
					pstmt.setInt(2, toInsert.get(i).getNumS());
					pstmt.setTimestamp(3, new Timestamp((new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(toInsert.get(i).getDate()).getTime()));
					pstmt.setInt(4, toInsert.get(i).getZone());
					pstmt.setInt(5, toInsert.get(i).getQt());			
					int nb_insert = pstmt.executeUpdate();

					if (nb_insert != 1)
						throw new BDException("Problème lors du transfert de caddie");

					conn.commit();
				}
			}		
		} catch (SQLException e) {
			throw new BDException("Problème lors de la mise à jour du type du caddie (Code Oracle : "+e.getErrorCode()+")");
		} catch (ParseException e) {}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
			BDConnexion.FermerTout(null, pstmt, null);
		}
	}
	public static Vector<Ticket> valideCaddie (Vector<Caddie> caddies) throws BDException, ParseException {
		ArrayList<Vector<Place>> placesToChoice = new ArrayList<Vector<Place>>();
		ArrayList<Integer> placesPrice = new ArrayList<Integer>();
		Vector<Ticket> toReturn = new Vector<Ticket>();
		int finalPrice = 0 ;
		
		// Test et récupération des places disponibles.
		for(Caddie caddie : caddies){
			Vector<Place> places = getPlacesDisponiblesFromZone(Integer.toString(caddie.getNumS()), caddie.getDate(), caddie.getZone());

			// Test si le nombre de places est bon.
			if(places.size() < caddie.getQt())
				throw new BDException("Il ne reste plus assez de places !!!!");
			
			// Choix des places.
			placesToChoice.add(choicePlaces(places, caddie.getQt()));
			
			// Ajout au prix de la commande.
			int prix =getPriceOfAPlace(caddie.getZone());
			finalPrice+=prix*caddie.getQt();
			placesPrice.add(prix);
		}
		
		// Insertion.
		PreparedStatement stmt = null;
		Connection conn = null;
		String requete = null;
		try {
			conn = BDConnexion.getConnexion();
			conn.setAutoCommit(false);
			
			// Création d'une nouvelle commande.
			int numDossier = BDRequetesTest.testNouveauDossier(conn,finalPrice);
			
			// Ajout de tous les tickets
			for(int i = 0 ; i< caddies.size() ; i++){

				Caddie caddie = caddies.get(i);
				Vector<Place> places = placesToChoice.get(i);
				int prixDelaPlace = placesPrice.get(i);
				for(Place place : places){
					// Get le nouveau num de serie.
					int noserie = BDRequetesTest.testNouveauTicket(conn);
					requete = "insert into lestickets values (?, ?, ?, ?, ?, ?, ?)";
					stmt = conn.prepareStatement(requete);
					stmt.setInt(1,noserie);
					stmt.setInt(2,caddie.getNumS());
					stmt.setTimestamp(3,new Timestamp((new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(caddie.getDate()).getTime()));
					stmt.setInt(4,place.getNum());
					stmt.setInt(5,place.getRang());
					Date dNow = new Date( );
				    SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
					stmt.setTimestamp(6,new Timestamp((new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(ft.format(dNow)).getTime()));
					stmt.setInt(7,numDossier);
					int nb_insert = stmt.executeUpdate();
					if(nb_insert != 1)
					{
						// ERREUR CRITIQUE ....
						conn.rollback();
						throw new BDException("Impossible de valider la commande");
					}
					else
					{
						// Ajout d'un ticket à retourner.
						Ticket tik = new Ticket();
						tik.setDateEmission(ft.format(dNow));
						tik.setDateRep(caddie.getDate());
						tik.setNomS(caddie.getNom());
						tik.setNumDossier(numDossier);
						tik.setNumPlace(place.getNum());
						tik.setNumS(caddie.getNumS());
						tik.setNumTicket(noserie);
						tik.setRangPlace(place.getRang());
						tik.setZonePlace(place.getZone());
						tik.setPrix(prixDelaPlace);
						
						toReturn.add(tik);
					}
				}
			}
			
			// To c'est normalement passé.
			conn.commit();

		} catch (SQLException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, null);
		}
		return toReturn ;
	}
	
	public static Vector<Place> choicePlaces(Vector<Place> places, int qt){
		Vector<Place> toReturn = new Vector<Place>();
		
		int index = (int)(Math.random() * (places.size()));
		
		for(int i = 0 ; i < qt ; i++){
			toReturn.add(places.get(index));
			index++;
			index%=places.size();
		}
		
		return toReturn;
	}
	
	public static int getPriceOfAPlace(int zone) throws BDException{
		String requete = "select prix from lescategories c, leszones z where c.nomc = z.nomc and numz=" + zone;
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int res = 0 ;
		try {
			conn = BDConnexion.getConnexion();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			if(rs.next()) {
				res =rs.getInt(1);
			}
			else{
				throw new BDException("Problème dans l'interrogation, impossible de déterminer le prix. Hey c'est pas gratuit");
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des places (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}
	
	
}
