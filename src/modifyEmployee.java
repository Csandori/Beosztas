
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/modifyEmployee")
public class modifyEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// String username=request.getParameter("Username");
		// String password=request.getParameter("Password");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// connecting to database
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM employee");

			// Beolvassa, hogy melyik módosítás gombot nyomta meg
			int countDR = Integer.parseInt(request.getParameter("ID"));

			String selectMenu = new String();
			// HTMLt generál
			loop: for (int countID = 0; true; countID++) {
				if (countDR == countID) {
					out.println("<h1></h1><form action=\"modifyEmployeeExe.jsp\">");
					out.println("<input type=\"hidden\" name=\"topnavID\" value=\"2\">");
					out.println("<input type=\"hidden\" name=\"ID\" value=" + countID + ">");
					out.println("<table class=\"modifyEmployeeTable\">");
					out.println("<tr>");
					out.println("<th class =\"employeeth\">ID</th>");
					out.println("<td class=\"employeetd\">" + rs.getObject(1).toString() + "</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th class=\"employeeth\">Név</th>");
					out.println("<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" name=\"name\" value=\""
							+ rs.getObject(2).toString() + "\"></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th class=\"employeeth\">Szájseb</th>");
					out.println("<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" size=\"1\" name=\"szajseb\" value=\""
							+ rs.getObject(3).toString() + "\"></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th class=\"employeeth\">Fogszabi</th>");
					out.println("<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" size=\"1\" name=\"fogszabi\" value=\""
							+ rs.getObject(4).toString() + "\"></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th class=\"employeetd\">Inaktív</th>");
					out.println("<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" size=\"1\" name=\"inaktiv\" value=\""
							+ rs.getObject(5).toString() + "\"></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th class=\"employeeth\">Csoport</th>");
					if (rs.getObject(6).toString().contains("a")) {
						selectMenu = "<td class=\"employeetd\"><select class=\"select\" name=\"oda\">\r\n" + "		<option value=\"o\">Orvos</option>\r\n"
								+ "		<option value=\"d\">Dentalhigiénikus</option>\r\n"
								+ "		<option value=\"a\" selected>Asszisztens</option>\r\n" + "</select></td>";
					} else if (rs.getObject(6).toString().contains("d")) {
						selectMenu = "<td class=\"employeetd\"><select class=\"select\" name=\"oda\">\r\n" + "		<option value=\"o\">Orvos</option>\r\n"
								+ "		<option value=\"d\" selected>Dentalhigiénikus</option>\r\n"
								+ "		<option value=\"a\">Asszisztens</option>\r\n" + "</select></td>";
					} else {
						selectMenu = "<td class=\"employeetd\"><select class=\"select\" name=\"oda\">\r\n"
								+ "		<option value=\"o\" selected>Orvos</option>\r\n"
								+ "		<option value=\"d\">Dentalhigiénikus</option>\r\n"
								+ "		<option value=\"a\">Asszisztens</option>\r\n" + "</select></td>";

					}
					out.println(selectMenu);
					out.println("</tr></table>");
					out.println("<h1></h1>");
					out.println("<input type=\"submit\" class=\"modifyEmployeeButton\" value=\"Módosítás\"/>");
					out.println("</form>");
					break loop;
				} else {
					rs.next();
				}
			}

			out.println("</body></html>");
		} catch (

		SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
		} catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found.", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {
			}
		}
		out.close();
	}
}