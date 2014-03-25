package bdd.accessBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bdd.modeles.Representation;
import bdd.modeles.Spectacle;

public class BDRequetes {

	public static Vector<Representation> getRepresentations () throws BDException {
		Vector<Representation> res = new Vector<Representation>();
		String requete;
		Statement stmt;
		ResultSet rs;
		Connection conn = BDConnexion.getConnexion();

		requete = "select nomS,DATEREP from LesRepresentations, LesSpectacles where LesRepresentations.numS = LesSpectacles.numS";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Representation (rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des programmes (Code Oracle : "+e.getErrorCode()+")");
		}
		BDConnexion.FermerTout(conn, stmt, rs);
		return res;
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
	
}
