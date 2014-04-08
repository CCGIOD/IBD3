<%@ page pageEncoding="UTF-8" %>

<%@ page import="bdd.accessBD.BDRequetes"%> 
<%@ page import="bdd.accessBD.BDRequetesTest"%> 
<%@ page import="bdd.exceptions.BDException"%> 
<%@ page import="bdd.exceptions.BDExceptionIllegal"%>
<%@ page import="servlets.caddie.CaddieVirtuel"%> 
<%@ page import="bdd.modeles.Caddie"%>
<%@ page import="servlets.utils.ConvertHTML"%> 
<%@ page import="java.util.Vector"%> 
<%@ page import="java.text.ParseException"%> 
<%@ page import="jsp.*"%> 

<% ServletOutputStream _out = response.getOutputStream(); 
   response.setContentType("text/html"); 
   response.setCharacterEncoding( "iso-8859-1" ); %>
<%! String cad_ok = null; %>

<% Utils.header(_out,"Réservation de places de spectacle"); %>
<% if (!Utils.testConnection(session,_out)){ Utils.footer(_out); return; } %>

<%
		String nomS, numS, date, zone, nofp, c, work;
		nomS = request.getParameter("nomS");
		numS = request.getParameter("numS");
		date = request.getParameter("date");
		zone = request.getParameter("zone");
		nofp = request.getParameter("nofp");
		work = request.getParameter("work");
		c = request.getParameter("c");

		if (numS == null || date == null || nomS == null){
			out.clear();
			response.sendRedirect("programme.jsp");
			return;
		}
		else
			date=date.replaceAll("%20", " ");

		if (zone == null || zone == ""){		
			try {
				BDRequetesTest.testParametreReservation(nomS, date, numS);
			} catch (BDExceptionIllegal e) {
				out.clear();
				response.sendRedirect("programme.jsp");
				return;
			} catch (BDException e) {
				_out.println("<h1>"+e.getMessage()+"</h1>");
			}

			try {
				_out.println("<h2> Pour la représentation du spectacle : <br>\""+nomS+"\" le "+date+"</h2>");
				_out.println("<p><i>"+ConvertHTML.vectorZoneToHTML(BDRequetes.getZones(),numS, nomS, date)+"</i></p>");
			} catch (BDException e) {
				_out.println("<h1>"+e.getMessage()+"</h1>");
			}

		}

       if (numS != null && date != null && c != null && nomS != null && nofp != null && work != null && work.compareTo("R") == 0){
			try {
				String[] infos = BDRequetesTest.testAjoutCaddie(numS, date, zone);
				Caddie tmpCad = new Caddie(1, infos[0], date, Integer.parseInt(numS), Integer.parseInt(zone), infos[1], Integer.valueOf(nofp));
				Vector<Caddie> cads = new Vector<Caddie>();
				cads.add(tmpCad);
				Vector<Caddie> deleteC = null ;
				try {
					deleteC = BDRequetesTest.testCheckValideCaddie(cads, 0);
				} catch (ParseException e1) {
				}
				if(deleteC != null && deleteC.size() > 0){
					_out.println("<p><i>" + ConvertHTML.vectorCaddieDeletionToHTML(deleteC) + "</i></p>");
				}
				else{
					try {
						_out.println("<p><i>" + ConvertHTML.vectorTicketsToHTML(BDRequetes.valideCaddie(cads)));
					} catch (ParseException e) {
					}
				}
				
			} catch (BDException e) {
				_out.println("<h1>"+e.getMessage()+"</h1>");
			}	
		}
		else if (numS != null && date != null && c != null && nomS != null && nofp != null){
			try {
				if (session.getAttribute("config").toString().compareTo("P") == 0)
					BDRequetes.addRepresentationCaddie(numS, date, c, nofp);
				else 
					CaddieVirtuel.ajouterRep(numS, date, c, nofp);
				cad_ok=c;
				out.clear();
				response.sendRedirect("reservation.jsp?numS="+numS+"&date="+date+"&nomS="+nomS);
				return;
			} catch (BDException e) {
				_out.println("<h1>"+e.getMessage()+"</h1>");
			}				
		}

		if (cad_ok != null){
			_out.println("<h4> Votre réservation en zone "+cad_ok+" pour la représentation du spectacle \""+nomS+"\" le "+date+" a été ajoutée au caddie ! <i><a href=\"caddie.jsp\">(Voir le caddie)</a></i></h4>");
			cad_ok=null;
		}
%>

<% Utils.footer(_out); %>