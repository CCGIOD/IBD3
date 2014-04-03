<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="servlets.caddie.CaddieVirtuel"%>
<%@ page import="java.text.ParseException"%>
<%@ page import="jsp.*"%>

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% Utils.header(_out,"Consultation du caddie"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		String idp, idm, idd;
		idp = request.getParameter("idp");
		idm = request.getParameter("idm");
		idd = request.getParameter("idd");

		if (idp != null && idm == null && idd == null){
			if (session.getAttribute("config").toString().compareTo("P") == 0)
				try {
					BDRequetes.setQtCaddie(idp, '+');
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				} catch (BDException e) {
					_out.println("<h1>"+e.getMessage()+"</h1>");
				}
			else
				try {
					CaddieVirtuel.setQt(idp, '+');
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				} catch (ParseException e) {
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				}
		}
		else if (idp == null && idm != null && idd == null){
			if (session.getAttribute("config").toString().compareTo("P") == 0)
				try {
					BDRequetes.setQtCaddie(idm, '-');
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				} catch (BDException e) {
					_out.println("<h1>"+e.getMessage()+"</h1>");
				}
			else
				try {
					CaddieVirtuel.setQt(idm, '-');
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				} catch (ParseException e) {
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				}
		}
		else if (idp == null && idm == null && idd != null){
			if (session.getAttribute("config").toString().compareTo("P") == 0)
				try {
					BDRequetes.setQtCaddie(idd, 'd');
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				} catch (BDException e) {
					_out.println("<h1>"+e.getMessage()+"</h1>");
				}
			else
				try {
					CaddieVirtuel.setQt(idd, 'd');
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				} catch (ParseException e) {
					out.clear();
					response.sendRedirect("caddie.jsp");
					return;
				}
		}

		if (session.getAttribute("config").toString().compareTo("P") == 0){
			try {
				_out.println("<p><i>" + ConvertHTML.vectorCaddieToHTML(BDRequetes.getContenuCaddie())  + "</i></p>");
			} catch (BDException e) {
				_out.println("<h1>"+e.getMessage()+"</h1>");
			}
		}
		else{
			_out.println("<p><i>" + ConvertHTML.vectorCaddieToHTML(CaddieVirtuel.getList())  + "</i></p>");
		}
%>

<% Utils.footer(_out); %>