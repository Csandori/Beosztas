
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class topNav
 */
@WebServlet("/topNav")
public class topNav extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public topNav() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int activePage = 1;
		String printout = new String();
		if (request.getParameter("topnavID")==null) {
			 activePage = 2;
		} else {
		 activePage = Integer.parseInt(request.getParameter("topnavID")) - 1;
		}
		// linkek megadása
		String[][] links = new String[3][2];
		int linksLength = links.length;
		links[0][0] = "Kezdõlap";
		links[0][1] = "index2.jsp?topnavID=1";
		links[2][0] = "Beosztás";
		links[2][1] = "beosztas.jsp?topnavID=3";
		links[1][0] = "Munkatársak";
		links[1][1] = "employee.jsp?topnavID=2";

		printout = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\r\n"
				+ "<script src=\"js.js\"></script>\r\n"
				+ "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n"
				+ "</head>\r\n" + "<body>\r\n" + " <div class=\"topnav\">";

		for (int i = 0; i < linksLength; i++) {
			printout = printout + " <a";

			if (i == activePage) {
				printout = printout + " class=\"active\"";
			}
			printout = printout + " href=\"" + links[i][1] + "\">" + links[i][0] + "</a>\r\n";
		}

		printout = printout + "<a style=\"float:right\" onclick=\"goBack()\">Vissza</a>\r\n" + "  </div>";
		response.getWriter().append(printout);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
