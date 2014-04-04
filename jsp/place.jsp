<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="jsp.*"%> 

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% Utils.header(_out,"Places disponibles"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		String numS, date, nomS;
		numS = request.getParameter("numS");
		date = request.getParameter("date");
		nomS = request.getParameter("nomS");
		
		
		if (numS == null || date == null || nomS == null){
			out.clear();
			response.sendRedirect("programme.jsp");
			return;
		}
		else
			date=date.replaceAll("%20", " ");
		
		try {
			_out.println("<h2>Spectacle : \""+nomS+"\"<br>Représentation : \""+date+"\"</h2>");
			_out.println("<p><i>"+ConvertHTML.vectorPlaceToHTML(BDRequetes.getPlacesDisponibles(numS, date))+"</i></p>");
		} catch (BDException e) {
			_out.println("<h1>"+e.getMessage()+"</h1>");
		}
%>

<% Utils.footer(_out); %>