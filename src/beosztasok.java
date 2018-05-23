
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/beosztasok")
public class beosztasok extends HttpServlet {
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
		String szoveg = "<h1>Kérlek válassz évet!</h1>";
		try {

			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT Year FROM beosztas.beosztasok");
			int evSzam = 0;
			// displaying records
			while (rs.next()) {

				szoveg = szoveg
						+ "<form action=\"listBeosztasok.jsp\"><input type=\"hidden\" name=\"topnavID\" value=\"3\"></input><button type=\"submit\" name=\"Year\" Value =\""
						+ rs.getObject(1).toString() + "\" class=\"YearButton\">" + rs.getObject(1).toString()
						+ "</button></form>\r\n<h1></h1>";
				evSzam = Integer.parseInt(rs.getObject(1).toString()) + 1;
			}
			szoveg = szoveg
					+ "<form action=\"listBeosztasok.jsp\"><input type=\"hidden\" name=\"topnavID\" value=\"3\"></input><button type=\"submit\" name=\"Year\" Value =\""
					+ evSzam + "\" class=\"YearButton\">" + evSzam + "</button></form>\r\n<h1></h1>";
			out.println(szoveg);

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