package bdd.accessBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bdd.modeles.Place;
import bdd.modeles.Representation;
import bdd.modeles.Spectacle;

public class BDRequetes {

	public static Vector<Representation> getRepresentations (String numS) throws BDException {
		Vector<Representation> res = new Vector<Representation>();
		String requete;
		Statement stmt;
		ResultSet rs;
		Connection conn = BDConnexion.getConnexion();

		if (numS == null)
			requete = "select nomS, TO_CHAR(DATEREP, 'DD/MM/YYYY HH24:MI') AS DATEREP, LesRepresentations.numS from LesRepresentations, LesSpectacles where LesRepresentations.numS = LesSpectacles.numS";
		else{
			BDRequetesTest.testNumSpectable(conn, numS);
			requete = "select nomS, TO_CHAR(DATEREP, 'DD/MM/YYYY HH24:MI') AS DATEREP, LesRepresentations.numS from LesRepresentations, LesSpectacles where LesRepresentations.numS = LesSpectacles.numS and LesSpectacles.numS="+numS;
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Representation (rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des représentations (Code Oracle : "+e.getErrorCode()+")");
		}
		BDConnexion.FermerTout(conn, stmt, rs);
		return res;
	}

	public static Vector<Representation> getRepresentations () throws BDException {
		return getRepresentations(null);
	}

	public static Vector<Spectacle> getSpectables () throws BDException {
		Vector<Spectacle> res = new Vector<Spectacle>();
		String requete;
		Statement stmt;
		ResultSet rs;
		Connection conn = BDConnexion.getConnexion();

		requete = "select DISTINCT numS, nomS from LesSpectacles";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Spectacle(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des spectacles (Code Oracle : "+e.getErrorCode()+")");
		}
		BDConnexion.FermerTout(conn, stmt, rs);
		return res;
	}
	
	public static Vector<Place> getPlacesDisponibles (String numS, String date) throws BDException {
		Vector<Place> res = new Vector<Place>();
		String requete;
		Statement stmt;
		ResultSet rs;
		Connection conn = BDConnexion.getConnexion();

		BDRequetesTest.testRepresentation(conn, numS, date);
		
		requete = "select norang,noplace from lesplaces MINUS select norang, noplace from LesTickets where numS = "+numS+"  and dateRep = to_date('"+date+"','dd/mm/yyyy hh24:mi') ORDER BY norang, noplace";
				
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Place(rs.getInt(1), rs.getInt(2)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des places (Code Oracle : "+e.getErrorCode()+")");
		}
		BDConnexion.FermerTout(conn, stmt, rs);
		return res;
	}
}
