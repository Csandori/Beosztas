
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/listEmployee")
public class listEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// String username=request.getParameter("Username");
		// String password=request.getParameter("Password");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<h1></h1>");

		// connecting to database
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		int countID = 0;
		try {
			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM employee");
			out.println("<table align=\"center\">");
			out.println("<tr>");
			out.println("<th>ID</th>");
			out.println("<th>Név</th>");
			out.println("<th>Szájseb</th>");
			out.println("<th>Fogszabi</th>");
			out.println("<th>Inaktív</th>");
			out.println("<th>Csoport</th>");
			out.println("<th></th></tr>");
			String nullJel = "-";
			String plusJel = "+";
			// displaying records
			while (rs.next()) {

				countID++;
				out.println("<tr class=\"listBeosztastr\"><form action=\"modifyEmployee.jsp\">");
				out.println("<input type=\"hidden\" name=\"ID\" value=" + countID + ">");
				out.println("<input type=\"hidden\" name=\"topnavID\" value=\"2\">");

				out.println("<td class=\"listBeosztastd\">" + rs.getObject(1).toString() + "</td>");
				out.println("<td class=\"listBeosztastd\">" + rs.getObject(2).toString() + "</td>");
				if (rs.getObject(3).toString().contains("1")) {
					out.println("<td class=\"listBeosztastd\">" + plusJel + "</td>");
				} else {
					out.println("<td class=\"listBeosztastd\">" + nullJel + "</td>");
				}

				if (rs.getObject(4).toString().contains("1")) {
					out.println("<td class=\"listBeosztastd\">" + plusJel + "</td>");
				} else {
					out.println("<td class=\"listBeosztastd\">" + nullJel + "</td>");
				}

				if (rs.getObject(5).toString().contains("1")) {
					out.println("<td class=\"listBeosztastd\">" + plusJel + "</td>");
				} else {
					out.println("<td class=\"listBeosztastd\">" + nullJel + "</td>");
				}

				if (rs.getObject(6).toString().contains("o")) {
					out.println("<td class=\"listBeosztastd\">Orvos</td>");
				} else if (rs.getObject(6).toString().contains("a")) {
					out.println("<td class=\"listBeosztastd\">Asszisztens</td>");
				} else if (rs.getObject(6).toString().contains("d")) {
					out.println("<td class=\"listBeosztastd\">Dentalhigiénikus</td>");
				}
				out.println("<td class=\"listBeosztastd\">");
				out.println("<input type=\"submit\" value=\"Módosítás\" class=\"modifyBeosztasButton\" />\r\n"
						+ "	</form></td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("<form action=\"newEmployee.jsp\">");
			out.println("<input type=\"hidden\" name=\"topnavID\" value=\"2\">");
			out.println("<input type=\"submit\" class=\"newEmployee\" value=\"Új munkatárs hozzáadása\" />\r\n"
					+ "	</form>");

		} catch (SQLException e) {
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

	}
}