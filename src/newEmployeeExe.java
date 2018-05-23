
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

@WebServlet("/newEmployeeExe")
public class newEmployeeExe extends HttpServlet {
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

			// Beolvassa, hogy melyik módosítás gombot nyomta meg
			String dID = request.getParameter("ID");
			String dName = request.getParameter("name");
			String dSzajseb = request.getParameter("szajseb");
			String dFogszabi = request.getParameter("fogszabi");
			String dInaktiv = request.getParameter("inaktiv");
			String dCsoport = request.getParameter("oda");
			String update = "INSERT INTO beosztas.employee SET name=\"" + dName + "\", szajseb=\"" + dSzajseb
					+ "\", fogszabi=\"" + dFogszabi + "\", inaktiv=\"" + dInaktiv + "\", ID=\"" + dID + "\", oda=\""
					+ dCsoport + "\"";

			PreparedStatement preparedStmt = con.prepareStatement(update);
			preparedStmt.executeUpdate();
			out.print(dName + " hozzáadva");
			// HTMLt generál
			out.println("</body></html>");
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
		out.close();
	}
}