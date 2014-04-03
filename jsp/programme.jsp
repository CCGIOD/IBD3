<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="jsp.*"%> 

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% Utils.header(_out,"Programme de la saison"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		try {
			_out.println(ConvertHTML.vectorProgrammeToHTML(BDRequetes.getRepresentations(null), false));
		} catch (BDException e) {
			_out.println("<h1>"+e.getMessage()+"</h1>");
		}
%>

<% Utils.footer(_out); %>