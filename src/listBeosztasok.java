
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

@WebServlet("/listBeosztasok")
public class listBeosztasok extends HttpServlet {
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
		int count = 4;
		String idForSet = new String();
		String szoveg = "";
		int hetSzam = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String YearData = request.getParameter("Year");
		try {

			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();

			rs = stmt
					.executeQuery("SELECT * FROM beosztasok WHERE Year = \'" + YearData + "\' ORDER BY Year asc, Week");
			out.println("<h1 align=\"center\">" + YearData + "</h1>");
			out.println("<table align=\"center\">");
			out.println("<tr>");
			out.println("<th class=\" listBeosztasth\">Hétfõ</th>");
			out.println("<th class=\" listBeosztasth\">Péntek</th>");
			out.println("<th class=\" listBeosztasth\">Hétszám</th><th class=\" listBeosztasth\"></th></tr>");

			// displaying records
			int SQLHetSzam = 0;

			while (rs.next()) {
				SQLHetSzam = Integer.parseInt(rs.getObject(3).toString());

				for (; hetSzam < SQLHetSzam || ((hetSzam > SQLHetSzam) && hetSzam < 60); hetSzam++) {
					cal.set(Calendar.WEEK_OF_YEAR, hetSzam);
					cal.set(Calendar.YEAR, Integer.parseInt(rs.getObject(2).toString()));
					cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					out.println("<tr class=\"listBeosztastr\"><td class=\" listBeosztastd\">"
							+ sdf.format(cal.getTime()) + "</td>");
					cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
					out.println("<td class=\" listBeosztastd\">" + sdf.format(cal.getTime())
							+ "</td><td class=\" listBeosztastd\">" + hetSzam + "</td>");
					out.println("<td class=\"listBeosztastd\"><form action=\"newBeosztas.jsp\">");
					out.println("<input type=\"hidden\" name=\"topnavID\" value=\"3\"></input>"
							+ "<input type=\"hidden\" name=\"Year\" value=\"" + YearData + "\"></input>"
							+ "<input type=\"hidden\" name=\"Week\" value=\"" + hetSzam + "\"></input>"
							+ "<input type=\"submit\" class=\"newBeosztasButton\" value=\"Új\"></input></form> </tr>");
				}
				cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(rs.getObject(3).toString()));
				cal.set(Calendar.YEAR, Integer.parseInt(rs.getObject(2).toString()));
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				out.println(
						"<form action=\"modifyBeosztas.jsp\"><tr class=\"listBeosztastr\"><td class=\" listBeosztastd\">"
								+ sdf.format(cal.getTime()) + "</td>");
				szoveg = szoveg + rs.getObject(1).toString() + "&Year=" + rs.getObject(2).toString() + "&Week="
						+ rs.getObject(3).toString();
				cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
				out.println("<td class=\" listBeosztastd\">" + sdf.format(cal.getTime()) + "</td>");
				out.println("<td class=\" listBeosztastd\">" + rs.getObject(3).toString() + "</td>");
				out.println("<input type=\"hidden\" name=\"topnavID\" value=\"3\"></input>\r\n<input type=\"hidden\" name=\"id\" value=\"" + rs.getObject(1).toString() + "\">");
						
				count = 4;
				out.println(
						"<td class=\" listBeosztastd\"><input type=\"submit\"  class=\"modifyBeosztasButton\" value=\"Módosítás\" />\r\n"
								+ "	</td>");
				out.println("</tr></form>");
				hetSzam++;
			}

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

		for (; hetSzam < 54; hetSzam++) {
			cal.set(Calendar.WEEK_OF_YEAR, hetSzam);
			cal.set(Calendar.YEAR, Integer.parseInt(YearData));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			out.println("<tr class=\"listBeosztastr\"><td class=\" listBeosztastd\">" + sdf.format(cal.getTime())
					+ "</td>");
			cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			out.println("<td class=\" listBeosztastd\">" + sdf.format(cal.getTime())
					+ "</td><td class=\" listBeosztastd\">" + hetSzam + "</td>");
			out.println("<td class=\"listBeosztastd\"><form action=\"newBeosztas.jsp\">");
			out.println("<input type=\"hidden\" name=\"topnavID\" value=\"3\"></input>"
					+ "<input type=\"hidden\" name=\"Year\" value=\"" + YearData + "\"></input>"
					+ "<input type=\"hidden\" name=\"Week\" value=\"" + hetSzam + "\"></input>"
					+ "<input type=\"submit\"  class=\"newBeosztasButton\" value=\"Új\"></input></form> </tr>");
		}
		out.println("</table>");
	}
}