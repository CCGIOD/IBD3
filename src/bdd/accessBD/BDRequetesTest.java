package bdd.accessBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDRequetesTest {

	public static void testNumSpectable (String numS) throws BDException {
		String requete;
		Statement stmt;
		ResultSet rs;
		Connection conn = BDConnexion.getConnexion();

		try{ Integer.parseInt(numS); }  
		catch(NumberFormatException nfe){ 
			throw new BDException("Problème de numéro de représentation (pas un entier)");
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
		BDConnexion.FermerTout(conn, stmt, rs);
	}	
}
