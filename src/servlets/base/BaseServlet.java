package servlets.base;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import servlets.caddie.CaddieVirtuel;

import bdd.accessBD.BDConnexion;
import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

	protected ServletOutputStream out;
	
	public static HttpSession session;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		session = req.getSession(true);  

		out = res.getOutputStream();
		res.setContentType("text/html"); 
	    res.setCharacterEncoding( "iso-8859-1" );

	}
	
	public void testCaddie () throws IOException {
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

	public boolean testConnection () throws IOException {
		Connection conn = null;
		boolean rep = false;
		try {
			conn = BDConnexion.getConnexion();			
			testCaddie();
			rep = true;
		} catch (BDException e) {
			out.println("<h1 class=\"errortest\">Erreur : le test de connexion à la Base de données à échoué.</h1>");
			rep = false;
		}
		finally { if (conn != null) BDConnexion.FermerTout(conn, null, null); }
		return rep;
	}

	public void header (String... str) throws IOException {
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
		out.println("<BODY><div id=\"block\"><img border=\"0\" src=\"/images/theatre_serv.jpg\" width=\"200\" height=\"200\">");
		out.println("<h1>"+h1+" :</h1>");
	}

	public void footer () throws IOException {
		out.println("<hr><p class=\"backlink\"><a href=\"/index.html\">Page d'accueil</a></p>");
		out.println("</div></BODY></HTML>");
		out.close();
	}

	public void redirect (HttpServletResponse res, String url) throws IOException {
		res.sendRedirect(url);
		out.close();
	}
}
