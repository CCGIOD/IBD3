package bdd.accessBD;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import bdd.exceptions.BDException;

public class BDConnexion {

	public static Connection getConnexion () throws BDException {
		Connection conn = null;
		try {
			Properties p = new Properties();
			InputStream is = null;
			is = new FileInputStream("connection.conf");
			p.load(is);
			String url = p.getProperty("url");
			String driver = p.getProperty("driver");
			String login = p.getProperty("user");
			String mdp = p.getProperty("mdp");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,login,mdp);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new BDException("Connexion impossible à la base de données");
		} catch (IOException e) {
			throw new BDException("Fichier conf illisible");
		} catch (ClassNotFoundException e) {
			throw new BDException("Problème d'identification du pilote");
		}
		return conn;
	}
	
	public static void FermerTout (Connection conn, Statement stmt, ResultSet rs){
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
			conn = null;
		}
	}
	
	public static void FermerTout (Connection conn, PreparedStatement stmt, ResultSet rs){
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
			conn = null;
		}
	}
}
