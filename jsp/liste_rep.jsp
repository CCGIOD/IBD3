<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="servlets.base.BaseServlet"%> 
<%@ page import="servlets.utils.ConvertHTML"%> 

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% jsp.Utils.header(_out,"Programme de la saison"); %>
<% if (!jsp.Utils.testConnection(session,_out)){ jsp.Utils.footer(_out); return; } %>

<%
		try {
			_out.println(ConvertHTML.vectorProgrammeToHTML(BDRequetes.getRepresentations(null), false));
		} catch (BDException e) {
			_out.println("<h1>"+e.getMessage()+"</h1>");
		}
%>

<% jsp.Utils.footer(_out); %>