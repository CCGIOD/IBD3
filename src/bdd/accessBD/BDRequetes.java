package bdd.accessBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import bdd.exceptions.BDException;
import bdd.exceptions.BDExceptionIllegal;
import bdd.exceptions.BDExceptionParamError;
import bdd.modeles.Place;
import bdd.modeles.Representation;
import bdd.modeles.Spectacle;

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

	public static Vector<Place> getPlacesDisponibles (String numS, String date) throws BDException {
		Vector<Place> res = new Vector<Place>();
		String requete = "select norang,noplace from lesplaces MINUS select norang, noplace from LesTickets where numS = "+numS+"  and dateRep = to_date('"+date+"','dd/mm/yyyy hh24:mi') ORDER BY norang, noplace";;
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = BDConnexion.getConnexion();
			
			BDRequetesTest.testRepresentation(conn, numS, date);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				res.addElement(new Place(rs.getInt(1), rs.getInt(2)));
			}
		} catch (SQLException e) {
			throw new BDException("Problème dans l'interrogation des places (Code Oracle : "+e.getErrorCode()+")");
		}
		finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}

	public static String insertRepresentation(String numS, String dateRep, String heureRep) throws BDException, BDExceptionParamError {
		String requete = "insert into LESREPRESENTATIONS values ('"+numS+"',to_date('"+dateRep+" "+heureRep+"','dd/mm/yyyy hh24:mi'))";;
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
			conn.commit();
			
			if(nb_insert != 1)
				throw new BDException("Problème, impossible d'insérer une nouvelle représentation.");

		} catch (SQLException e) {
			throw new BDException("Problème, impossible d'insérer une nouvelle représentation.");
		} catch (ParseException e) {}
		finally {
			BDConnexion.FermerTout(conn, stmt, null);
		}

		return " .... " + nomSpectacle + " .... " + requete;
	}
}
