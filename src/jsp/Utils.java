package jsp;

import java.io.IOException;
import javax.servlet.ServletOutputStream;

public class Utils {

	public static void header (ServletOutputStream out, String... str) throws IOException {
		String title, h1;
		if (str.length == 1)
			title = h1 = str[0];
		else{
			title = str[0];
			h1 = str[1];
		}

		out.println("<HTML><HEAD>");
		out.println("<TITLE>"+title+"</TITLE>");
		out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\">");
		out.println("<meta charset=\"iso-8859-1\">");
		out.println("</HEAD>");
		out.println("<BODY><div id=\"block\">");
		out.println("<h1>"+h1+" :</h1>");
	}
	
	public static void footer (ServletOutputStream out) throws IOException {
		out.println("<hr><p class=\"backlink\"><a href=\"/jsp/index.jsp\">Page d'accueil</a></p>");
		out.println("</div></BODY></HTML>");
		out.close();
	}
}
