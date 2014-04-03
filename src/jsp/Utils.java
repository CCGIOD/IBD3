package jsp;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import servlets.caddie.CaddieVirtuel;
import bdd.accessBD.BDConnexion;
import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

public class Utils {

	public static void header (ServletOutputStream out, String... str) throws IOException {
		String title, h1;
		if (str.length == 1)
			title = h1 = str[0];
		else{
			title = str[0];
			h1 = str[1];
		}

		out.println("<HTML><HEAD>");
		out.println("<TITLE>"+title+"</TITLE>");
		out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"/style.css\">");
		out.println("<meta charset=\"iso-8859-1\">");
		out.println("</HEAD>");
		out.println("<BODY><div id=\"block\">");
		out.println("<h1>"+h1+" :</h1>");
	}
	
	public static void footer (ServletOutputStream out) throws IOException {
		out.println("<hr><p class=\"backlink\"><a href=\"/jsp/index.jsp\">Page d'accueil</a></p>");
		out.println("</div></BODY></HTML>");
		out.close();
	}
	
	private static void testCaddie (HttpSession session, ServletOutputStream out) throws IOException {
		if (session.getAttribute("config") == null) {  
			try {
				char d = BDRequetes.getTypeCaddie();

				if (d == 'P'){	    		
					BDRequetes.checkCaddieLifetime();
					session.setAttribute("config", "P");
				}
				else {
					int d2 = BDRequetes.getCaddieLifetime();
					if (d2 == -1){
						CaddieVirtuel.vider();
					}
					else if (d2 > 0){
						CaddieVirtuel.checkDate(d2);
					}
					session.setAttribute("config", "V");
				}
			} catch (BDException e) {
				out.println("<h1>"+e.getMessage()+"</h1>");
			}
		}
	}

	public static boolean testConnection (HttpSession session, ServletOutputStream out) throws IOException {
		Connection conn = null;
		boolean rep = false;
		try {
			conn = BDConnexion.getConnexion();			
			testCaddie(session, out);
			rep = true;
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
			rep = false;
		}
		finally { if (conn != null) BDConnexion.FermerTout(conn, null, null); }
		return rep;
	}
}
