<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="servlets.utils.ConvertHTML"%>
<%@ page import="bdd.modeles.Caddie"%>
<%@ page import="servlets.caddie.CaddieVirtuel"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.text.ParseException"%>
<%@ page import="jsp.*"%>

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>

<% Utils.header(_out,"Consultation du caddie"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		String idp, idm, idd, valider;
		idp = request.getParameter("idp");
		idm = request.getParameter("idm");
		idd = request.getParameter("idd");
		valider = request.getParameter("valider");

		if(valider != null){
			if (session.getAttribute("config").toString().compareTo("P") == 0){
				try {
					Vector<Caddie> caddie = BDRequetes.getContenuCaddie() ;
					if(caddie.size()>0){
						try {
							_out.println("<p><i>" + ConvertHTML.vectorTicketsToHTML(BDRequetes.valideCaddie(caddie)));
							BDRequetes.deleteCaddie();
						} catch (ParseException e) {
						}
					}
				} catch (BDException e) {
					_out.println("<h1>"+e.getMessage()+"</h1>");
				}
			}
			else
			{
				try {
					Vector<Caddie> caddie = CaddieVirtuel.getList();
					if(caddie.size()>0){
						try {
							_out.println("<p><i>" + ConvertHTML.vectorTicketsToHTML(BDRequetes.valideCaddie(caddie)));
							CaddieVirtuel.vider() ;
							
						} catch (ParseException e) {
						}
					}
				} catch (BDException e) {
					_out.println("<h1>"+e.getMessage()+"</h1>");
				}
			}
		}

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