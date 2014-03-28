package servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import bdd.accessBD.BDRequetes;
import bdd.exceptions.BDException;

import java.io.IOException;

@SuppressWarnings("serial")
public class ConfigurationServlet extends _BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
		header("Page de configuration", "Configuration");
		if (!testConnection()){ footer(); return; }

		String val;
		val = req.getParameter("val");

		if (val != null){
			boolean valide = false;
			if ((val.compareTo("SESSION") == 0 || val.compareTo("NOLIMIT") == 0))
				valide=true;
			else{
				try {
					int i = Integer.parseInt(val);
					if (i < 0)
						throw new Exception();
					else
						valide = true;
				} 
				catch (Exception e) {}
			}

			if (valide)
				try {
					BDRequetes.majCaddieLifetime(val);
					redirect(res, "ConfigurationServlet"); return;
				} catch (BDException e) {
					out.println("<h1>"+e.getMessage()+"</h1>");
				}
		}
	
		try {
			int d = BDRequetes.getCaddieLifetime();
			String d2;
			if (d == -1)
				d2 = "SESSION";
			else if (d == -2)
				d2 = "SANS LIMITE";
			else
				d2 = d+" JOURS";			

			out.println("<h2> La durée de vie du caddie actuelle est : "+d2+"</h2>");
		} catch (BDException e) {
			out.println("<h1>"+e.getMessage()+"</h1>");
		}

		out.println("<TABLE BORDER='1' width=\"800\"><CAPTION>Durée de vie du caddie :</CAPTION>");

		out.println("<TR><TH><a href=\"ConfigurationServlet?val=SESSION\">SESSION</a></TH>");
		out.println("<TH><form action=\"\"><input type=\"number\" min=\"1\" step=\"1\" pattern=\"\\d+\" name=\"val\"> <input type=\"submit\" value=\"Submit\"></form></TH>");
		out.println("<TH><a href=\"ConfigurationServlet?val=NOLIMIT\">SANS LIMITE</a></TH>");

		out.println("</TR></TABLE>");

		footer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		doGet(req, res);
	}

	public String getServletInfo() {
		return "Permet de configurer l'application";
	}

	public void footer() throws IOException {
		out.println("<hr><p><a href=\"/admin/admin.html\">Page d'administration</a></p>");
		super.footer();
	}
}
