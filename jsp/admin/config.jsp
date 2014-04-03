<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="servlets.caddie.CaddieVirtuel"%> 
<%@ page import="jsp.*"%> 

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% Utils.header(_out,"Page de configuration", "Configuration"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		String val, val2;
		val = request.getParameter("val");
		val2 = request.getParameter("val2");

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
					out.clear();
					response.sendRedirect("config.jsp");
					return;
				} catch (BDException e) {
					_out.println("<h1>"+e.getMessage()+"</h1>");
				}
		}

		if (val2 != null
				&& (val2.compareTo("PERSISTANT") == 0 || val2.compareTo("VOLATILE") == 0)
				&& val2.charAt(0) != session.getAttribute("config").toString().charAt(0)){
			try {
				if (val2.compareTo("PERSISTANT") == 0)
					BDRequetes.majTypeCaddie(val2,CaddieVirtuel.list);
				else{
					BDRequetes.majTypeCaddie(val2, null);
					CaddieVirtuel.list=BDRequetes.getContenuCaddie();
				}
				session.setAttribute("config", val2.charAt(0));
				out.clear();
				response.sendRedirect("config.jsp");
				return;
			} catch (BDException e) {
				_out.println("<h1>"+e.getMessage()+"</h1>");
			}
		}

		try {
			char d = BDRequetes.getTypeCaddie();
			String t="";
			if (d == 'P')
				t = "PERSISTANT";
			else
				t = "VOLATILE";

			_out.println("<h2> Le type du caddie actuelle est : <b>"+t+"</b></h2>");
		} catch (BDException e) {
			_out.println("<h1>"+e.getMessage()+"</h1>");
		}

		_out.println("<TABLE BORDER='1' width=\"800\"><CAPTION>Type du caddie :</CAPTION>");
		_out.println("<TR><TH><a href=\"config.jsp?val2=PERSISTANT\">PERSISTANT</a></TH>");
		_out.println("<TH><a href=\"config.jsp?val2=VOLATILE\">VOLATILE</a></TH>");
		_out.println("</TR></TABLE>");

		try {
			int d = BDRequetes.getCaddieLifetime();
			String d2="";
			if (d == -1)
				d2 = "SESSION";
			else if (d == -2)
				d2 = "SANS LIMITE";
			else if (d > 0)
				d2 = d+" JOURS";			

			_out.println("<h2> La durée de vie du caddie actuelle est : <b>"+d2+"</b></h2>");
		} catch (BDException e) {
			_out.println("<h1>"+e.getMessage()+"</h1>");
		}

		_out.println("<TABLE BORDER='1' width=\"800\"><CAPTION>Durée de vie du caddie :</CAPTION>");
		_out.println("<TR><TH><a href=\"config.jsp?val=SESSION\">SESSION</a></TH>");
		_out.println("<TH><form action=\"\"><input type=\"number\" min=\"1\" step=\"1\" pattern=\"\\d+\" name=\"val\"> <input type=\"submit\" value=\"Submit\"></form></TH>");
		_out.println("<TH><a href=\"config.jsp?val=NOLIMIT\">SANS LIMITE</a></TH>");
		_out.println("</TR></TABLE>");
%>

<% _out.println("<hr><p class=\"backlink\"><a href=\"admin.jsp\">Page d'administration</a></p>"); %>
<% Utils.footer(_out); %>