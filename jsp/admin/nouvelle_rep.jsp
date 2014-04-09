<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%>
<%@ page import="bdd.exceptions.BDException"%>
<%@ page import="bdd.exceptions.BDExceptionParamError"%>
<%@ page import="jsp.*"%> 

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% Utils.header(_out,"Ajouter une nouvelle représentation"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		String numS, dateS, heureS;
		numS = request.getParameter("numS");
		dateS = request.getParameter("date");
		heureS = request.getParameter("heure");

		try {
			_out.println("<p><i>" + ConvertHTML.vectorSpectacleToHTML(BDRequetes.getSpectables())  + "</i></p>");
		} catch (BDException e) {
			_out.println("<h1>"+e.getMessage()+"</h1>");
		}

		boolean error = false ;

		// Cas de l'insertion dans la base de données
		if ((numS != null && dateS != null && heureS != null) && (numS != "" && dateS != "" && heureS != "")){
			String nomSpectacle = null ;
			try {
				nomSpectacle = BDRequetes.insertRepresentation(numS, dateS, heureS);
			} catch(BDExceptionParamError e1){
				_out.println("<h1>"+e1.getMessageError()+"</h1>");
				if(e1.getParamsError().contains(1)){
					numS="";
					error = true ;
				}
				if(e1.getParamsError().contains(2)){
					dateS="";
					error = true ;
				}
				if(e1.getParamsError().contains(3)){
					heureS="";
					error = true ;
				}
			} catch (BDException e2) {
				_out.println("<h1>"+e2.getMessage()+"</h1>");
				error = true ;
			}

			if(!error){
				_out.println("<p><i>Vous venez d'ajouter la représentation suivante :</i></p>");
				_out.println("<p><i>Pour le spectacle "+nomSpectacle + " (" + numS + ")" +" à l'horaire " + dateS + " " + heureS +"</i></p>");
				_out.println("<p>Ajouter une autre représentation <a href=\"nouvelle_rep.jsp?numS="+numS+"\">à ce spectacle</a> ou <a href=\"/servlet/NouvelleRepresentationServlet\">à un autre spectacle</a></p>");

			}
		}

		// Cas affichage du formule (première fois ou error).
		if (((numS == null || dateS == null || heureS == null) || (numS == "" || dateS == "" || heureS == "")) || error ){

			_out.println("<P>");

			_out.println("Veuillez saisir les informations relatives &agrave; la nouvelle représentation :");
			_out.print("<form action=\"\"");
			_out.println("method=POST>");

			// Numero de spectacle.
			_out.println("Numéro de spectacle :");
			if(numS != "" &&  numS != null )
				_out.println("<input type=text value=\"" + numS + "\" size=20 name=numS>");
			else
				_out.println("<input type=text size=20 name=numS>");
			_out.println("<br>");

			// Date de la représentation
			_out.println("Date de la représentation :");
			if(dateS != "" &&  dateS != null)
				_out.println("<input type=text value=\"" + dateS + "\" size=20 name=date>");
			else
				_out.println("<input type=text size=20 name=date>");
			_out.println("<br>");

			// Heure de début de la représentation
			_out.println("Heure de début de la représentation :");
			if(heureS != "" &&  heureS != null)
				_out.println("<input type=text value=\"" + heureS + "\" size=20 name=heure>");
			else
				_out.println("<input type=text size=20 name=heure>");
			_out.println("<br>");
			_out.println("<input type=submit>");
			_out.println("<br>");

			// Test s'il y avait une erreur.
			if(numS == "" || dateS == "" || heureS == ""){
				_out.println("<span class=\"error\">Les informations que vous avez fourni sont incorrect.</span>");
			}
			_out.println("</form>");	
		}
%>

<% _out.println("<hr><p class=\"backlink\"><a href=\"admin.jsp\">Page d'administration</a></p>"); %>
<% Utils.footer(_out); %>