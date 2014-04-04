<%@ page pageEncoding="UTF-8" %>

<%  
 String realmName = "Administrateur";  
 response.setHeader("WWW-Authenticate","Basic realm=\"" + realmName + "\"");  
 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  
 %>  

 <!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="GENERATOR" content="Microsoft FrontPage 5.0">
    <meta name="Author" content="Sara Bouchenak">
    <title>Petit Théatre de l'Informatique</title>
  </head>
  <style>
	p a {text-decoration:none; color:#A60000;}
	p a:hover {text-decoration:underline; color:red;}
     	.lien {color:red;}
	.lien:hover {color:#A60000;}
	table {margin-bottom:50px;}
  </style>
  <body bgcolor="#FFFFFF" style="margin:0;">
    <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#FFFFFF" width="100%" id="AutoNumber1" height="1">
      <tr>
	<td width="25%" align="left" valign="top" height="1">
	  <font face="URW Palladio L">
	    <img border="0" src="/images/rideau-gauche.jpg"></font></td>
	<td width="50%" align="center" valign="top" height="65">
	  <font face="URW Palladio L">
	    <img border="0" src="/images/theatre_jsp.jpg" width="200" height="200"></font><p>
	    <font face="URW Palladio L" size="5"><center>Petit Théatre de l'Informatique</center></font></p>
	  <p align="left">&nbsp;</p>
	  <p align="left"><font face="URW Palladio L" style="font-size: 16pt"><center>
	      Vous n'êtes pas identifié !</center></font></p>
	  <p align="left"><font face="URW Palladio L" style="font-size: 26pt">
	      <a class="lien" href="/index.html"><center>RETOUR SITE AVEC SERVLETS</center></a><br></font></p>
	<p align="left"><font face="URW Palladio L" style="font-size: 26pt">
	      <a class="lien" href="/jsp/index.jsp"><center>RETOUR SITE EN JSP</center></a><br></font></p>
	</td>
	<td width="25%" align="right" valign="top" height="1">
	  <font face="URW Palladio L">
	    <img border="0" src="/images/rideau-droit.jpg"></font></td>
      </tr>
    </table>

  </body>
</html>