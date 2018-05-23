
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
import java.sql.*;

@WebServlet("/saveBeosztas")
public class saveBeosztas extends HttpServlet {
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
		String szoveg = new String();
		String ideiglenesSzoveg = new String();

		String idForSet = new String();
		String[] parts = request.getParameter("date10").split("\\.");
		String year = parts[0];
		String month = parts[1];
		String day = parts[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.");
		Calendar ca1 = Calendar.getInstance();
		ca1.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
		int weekNumber;
		weekNumber = ca1.get(Calendar.WEEK_OF_YEAR);
		String textHetfo = request.getParameter("textHetfo");
		String textKedd = request.getParameter("textKedd");
		String textSzerda = request.getParameter("textSzerda");
		String textCsutortok = request.getParameter("textCsutortok");
		String textPentek = request.getParameter("textPentek");
		szoveg = "";
		try {
			int sorokSzama = 1;
			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM beosztas.beosztasok");

			while (rs.next()) {
				sorokSzama++;
			}
			int beosztalapcount = (sorokSzama - 1) * 54 + 1;
			// rendelõ
			for (int i = 1; i < 7; i++) {
				// sor
				for (int j = 1; j < 4; j++) {
					// nap
					for (int k = 1; k < 6; k++) {
						// napszak
						for (int l = 1; l < 3; l++) {
							if (k == 5 && l == 2) {
								break;
							}
							// orvos/asszisztens
							for (int m = 1; m < 3; m++) {
								idForSet = Integer.toString(i) + j + k + l + m + "0";
								ideiglenesSzoveg = request.getParameter(idForSet);
								szoveg = szoveg + ",\r\n '" + ideiglenesSzoveg + "'";
							}
						}
					}
				}
			}
			String update2 = "INSERT INTO beosztas.beosztasalap VALUES ";
			ca1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			update2 = update2 + "('" + beosztalapcount + "', '" + sdf.format(ca1.getTime()) + "'";
			// nap
			for (int k = 1; k < 6; k++) {
				if (k == 1) {
					ca1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				} else if (k == 2) {
					ca1.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
				} else if (k == 3) {
					ca1.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
				} else if (k == 4) {
					ca1.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
				} else if (k == 5) {
					ca1.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
				}

				// napszak
				for (int l = 1; l < 3; l++) {
					// rendelõ
					for (int i = 1; i < 7; i++) {
						// sor
						for (int j = 1; j < 4; j++) {
							// elsõ sor
							if (j == 1) {
								// orvos/asszisztens
								for (int m = 1; m < 3; m++) {
									if (m == 1) {
										idForSet = Integer.toString(i) + j + k + l + m + "0";
										ideiglenesSzoveg = request.getParameter(idForSet);
										update2 = update2 + ", '" + l + "', '" + i;
										update2 = update2 + "' ,\r\n '" + ideiglenesSzoveg + "'";
									} else {
										idForSet = Integer.toString(i) + j + k + l + m + "0";
										ideiglenesSzoveg = request.getParameter(idForSet);
										update2 = update2 + ",\r\n '" + ideiglenesSzoveg + "'";
									}
								}
								// második sor
							} else if (j == 2) {
								// orvos/asszisztens
								for (int m = 1; m < 3; m++) {
									if (m == 1) {
										idForSet = Integer.toString(i) + j + k + l + m + "0";
										ideiglenesSzoveg = request.getParameter(idForSet);
										update2 = update2 + ",\r\n '" + ideiglenesSzoveg + "'";
									} else {
										idForSet = Integer.toString(i) + j + k + l + m + "0";
										ideiglenesSzoveg = request.getParameter(idForSet);
										update2 = update2 + ",\r\n '" + ideiglenesSzoveg + "'";
									}

								}
								// harmadik sor
							} else if (j == 3) {
								// orvos/asszisztens
								for (int m = 1; m < 3; m++) {
									if (m == 1) {
										idForSet = Integer.toString(i) + j + k + l + m + "0";
										ideiglenesSzoveg = request.getParameter(idForSet);
										update2 = update2 + ",\r\n '" + ideiglenesSzoveg + "'";
									} else {
										idForSet = Integer.toString(i) + j + k + l + m + "0";
										ideiglenesSzoveg = request.getParameter(idForSet);
										update2 = update2 + ",\r\n '" + ideiglenesSzoveg + "'";
									}
								}

							}
						}
						update2 = update2 + ")";
						PreparedStatement preparedStmt = con.prepareStatement(update2);
						preparedStmt.executeUpdate();
						update2 = "INSERT INTO beosztas.beosztasalap VALUES ";
						beosztalapcount++;
						update2 = update2 + "('" + beosztalapcount + "', '" + sdf.format(ca1.getTime()) + "'";
					}

					if (k == 5) {
						break;
					}
				}
			}

			String update = "INSERT INTO beosztas.beosztasok VALUES ('" + sorokSzama + "', '" + year + "', '"
					+ weekNumber + "'" + szoveg + ", '" + textHetfo + "', '" + textKedd + "', '" + textSzerda + "', '"
					+ textCsutortok + "', '" + textPentek + "')";

			PreparedStatement preparedStmt = con.prepareStatement(update);
			preparedStmt.executeUpdate();
			out.print("Beosztás elmentve!");
			// HTMLt generál
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