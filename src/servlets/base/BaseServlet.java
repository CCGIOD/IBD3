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

/**
 * Cette classe permet de factoriser le code des Servlets. 
 */
@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

	/**
	 * La variable out où l'on écrit le code HTML.
	 */
	protected ServletOutputStream out;

	/**
	 * La variable de session.
	 */
	public static HttpSession session;

	/**
	 * Méthode doGet de la Servlet.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		session = req.getSession(true);  

		out = res.getOutputStream();
		res.setContentType("text/html"); 
		res.setCharacterEncoding( "iso-8859-1" );

	}

	/**
	 * Permet de tester si le caddie est défini dans la session et de le définir sinon.
	 * @param conn Une connexion à la BDD.
	 */
	public void testCaddie (Connection conn) throws IOException, BDException {
		if (session.getAttribute("config") == null) {  
			char d = BDRequetes.getTypeCaddie(conn);

			if (d == 'P'){	    		
				BDRequetes.checkCaddieLifetime(conn);
				session.setAttribute("config", "P");
			}
			else {
				int d2 = BDRequetes.getCaddieLifetime(conn);
				if (d2 == -1){
					CaddieVirtuel.vider();
				}
				else if (d2 > 0){
					CaddieVirtuel.checkDate(d2);
				}
				session.setAttribute("config", "V");
			}
		}
	}

	/**
	 * Le test de connexion à faire avant de charger la page pour vérifier que la BD est accessible.
	 * @return True si la connexion est valide, false sinon.
	 */
	public boolean testConnection () throws IOException {
		Connection conn = null;
		boolean rep = false;
		try {
			conn = BDConnexion.getConnexion();
			testCaddie(conn);
			rep = true;
		} catch (BDException e) {
			out.println("<h1 class=\"errortest\">"+e.getMessage()+"</h1>");
			rep = false;
		}
		finally { BDConnexion.FermerTout(conn, null, null); }
		return rep;
	}

	/**
	 * Header de la page
	 * @param str Le titre de la page (title) et éventuellement un h1.
	 */
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

	/**
	 * Le footer de la page.
	 */
	public void footer () throws IOException {
		out.println("<hr><p class=\"backlink\"><a href=\"/index.html\">Page d'accueil</a></p>");
		out.println("</div></BODY></HTML>");
		out.close();
	}

	/**
	 * Permet de faire une redirection.
	 * @param res L'objet response.
	 * @param url L'url de redirection.
	 */
	public void redirect (HttpServletResponse res, String url) throws IOException {
		res.sendRedirect(url);
		out.close();
	}
}
