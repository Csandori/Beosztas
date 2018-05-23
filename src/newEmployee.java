
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/newEmployee")
public class newEmployee extends HttpServlet {
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
			int countID = 1;
			rs = stmt.executeQuery("SELECT * FROM employee");
			while (rs.next()) {
				countID++;
			}
			out.println("<h1></h1><form action=\"newEmployeeAdded.jsp\">");
			out.println("<input type=\"hidden\" name=\"ID\" value=" + countID + ">");
			out.println("<input type=\"hidden\" name=\"topnavID\" value=\"2\">");
			out.println("<table class=\"modifyEmployeeTable\">");
			out.println("<tr>");
			out.println("<th class =\"employeeth\">ID</th>");
			out.println("<td class=\"employeetd\">" +countID + "</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th class=\"employeeth\">Név</th>");
			out.println(
					"<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" name=\"name\"></input></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th class=\"employeeth\">Szájseb</th>");
			out.println(
					"<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" size=\"1\" name=\"szajseb\"></input></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th class=\"employeeth\">Fogszabi</th>");
			out.println(
					"<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" size=\"1\" name=\"fogszabi\"></input></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th class=\"employeetd\">Inaktív</th>");
			out.println(
					"<td class=\"employeetd\"><input type=\"text\" style=\"text-align: center;\" size=\"1\" name=\"inaktiv\"></input></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th class=\"employeeth\">Csoport</th>");
			out.println("<td class=\"employeetd\"><select class=\"select\" name=\"oda\">\r\n"
					+ "		<option value=\"o\" selected>Orvos</option>\r\n"
					+ "		<option value=\"d\">Dentalhigiénikus</option>\r\n"
					+ "		<option value=\"a\">Asszisztens</option>\r\n" + "</select></td>"

			);
			out.println("</tr></table>");
			out.println("<h1></h1>");
			out.println("<input class=\"modifyEmployeeButton\" type=\"submit\" value=\"Hozzáadás\"/>");

			out.println("</form>");
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