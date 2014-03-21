package bdd.accessBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bdd.modeles.Programme;

public class BDRequetes {

	public static Vector<Programme> getProgramme () throws BDException {
		Vector<Programme> res = new Vector<Programme>();
		String requete;
		Statement stmt;
		ResultSet rs;
		Connection conn = BDConnexion.getConnexion();

		requete = "select nomS,DATEREP from LesRepresentations, LesSpectacles where LesRepresentations.numS = LesSpectacles.numS";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Programme (rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des programmes (Code Oracle : "+e.getErrorCode()+")");
		}
		BDConnexion.FermerTout(conn, stmt, rs);
		return res;
	}
}
