<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="jsp.*"%> 

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% Utils.header(_out,"Liste des représentations"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		String numS;
		numS = request.getParameter("numS");
		
		try {
			_out.println("<p><i>" + ConvertHTML.vectorSpectacleConsultationToHTML(BDRequetes.getSpectables())  + "</i></p>");
		} catch (BDException e) {
			_out.println("<h1>"+e.getMessage()+"</h1>");
		}

		if (numS != null) {
			try {
				_out.println("<p><i>"+ConvertHTML.vectorProgrammeToHTML(BDRequetes.getRepresentations(numS), true)+"</i></p>");
			} catch (BDException e) {
				_out.println("<h1>"+e.getMessage()+"</h1>");
			}
		}
%>

<% Utils.footer(_out); %>