
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class beosztasMethods
 */
@WebServlet("/beosztasMethods")
public class beosztasMethods extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String[][] getEmployees() throws ServletException, IOException {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		int countID = 0;
		int result = 0;
		try {
			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM employee");
			while (rs.next()) {
				result++;
			}

			String[][] name = new String[result][3];
			Szemely[] szemelyek = new Szemely[result];
			rs.close();
			rs = null;
			rs = stmt.executeQuery("SELECT * FROM employee");

			while (rs.next()) {

				szemelyek[countID] = new Szemely();
				szemelyek[countID].setCsoport(rs.getObject(6).toString());
				szemelyek[countID].setID(rs.getObject(1).toString());
				szemelyek[countID].setSzajseb(rs.getObject(3).toString());
				szemelyek[countID].setFogszabi(rs.getObject(4).toString());
				szemelyek[countID].setInaktiv(rs.getObject(5).toString());
				name[countID][0] = rs.getObject(1).toString();
				name[countID][1] = "ID" + szemelyek[countID].getID() + "szajseb" + szemelyek[countID].getSzajseb()
						+ "fogszabi" + szemelyek[countID].getFogszabi();
				name[countID][2] = rs.getObject(2).toString();
				countID++;
			}
			return name;
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

	public String hiddenTable() throws ServletException, IOException {
		String eredmeny = "<table id=\"hiddenTable\" style=\"visibility:hidden\">";
		eredmeny = eredmeny + "<tr><td id=\"adat0\">0</td><td id=\"name0\"></td></tr>";
		String[][] adatok = getEmployees();
		for (int i = 0; i < adatok.length; i++) {
			eredmeny = eredmeny + "<tr><td id=\"ID" + (i + 1) + "\">" + adatok[i][0] + "</td><td id=\"adat" + (i + 1)
					+ "\">" + adatok[i][1] + "</td><td id=\"name" + (i + 1) + "\">" + adatok[i][2] + "</td></tr>\r\n";
		}
		eredmeny = eredmeny + "</table>";
		return eredmeny;
	}

	public String saveBeosztasButton() throws ServletException, IOException {
		String eredmeny = "<input class=\"beosztasOKButton\" type=\"submit\" value=\"Mentés\" style=\"float:right\" />\r\n"
				+ "	</form>\r\n";
		return eredmeny;
	}

	public String textInputline(boolean betoltes,Map<String,String> myMap)
			throws ServletException, IOException {
		String eredmeny = "";
		if (betoltes) {
			eredmeny = "<tr><td></td><td colspan=\"4\"><textarea class=\"textarea\" name=\"textHetfo\">"
					+ myMap.get("textHetfo") + "</textarea>"
					+ "</td><td colspan=\"4\"><textarea class=\"textarea\" name=\"textKedd\">"
					+ myMap.get("textKedd") + "</textarea></td>"
					+ "<td colspan=\"4\"><textarea class=\"textarea\" name=\"textSzerda\">"
					+ myMap.get("textSzerda") + "</textarea></td>"
					+ "<td colspan=\"4\"><textarea class=\"textarea\" name=\"textCsutortok\">"
					+ myMap.get("textCsutortok") + "</textarea></td>"
					+ "<td colspan=\"2\"><textarea class=\"textarea\" name=\"textPentek\">"
					+ myMap.get("textPentek") + "</textarea></td></tr>";
		} else {

			eredmeny = "<tr><td></td><td colspan=\"4\"><textarea class=\"textarea\" name=\"textHetfo\"></textarea>"
					+ "</td><td colspan=\"4\"><textarea class=\"textarea\" name=\"textKedd\"></textarea></td>"
					+ "<td colspan=\"4\"><textarea class=\"textarea\" name=\"textSzerda\"></textarea></td>"
					+ "<td colspan=\"4\"><textarea class=\"textarea\" name=\"textCsutortok\"></textarea></td>"
					+ "<td colspan=\"2\"><textarea class=\"textarea\" name=\"textPentek\">" + "</textarea></td></tr>";
		}
		eredmeny = eredmeny
				+ "<tr><td></td>\r\n<td colspan=\"4\" id=\"help1\" class=\"help\"></td>\r\n<td colspan=\"4\" id=\"help2\" class=\"help\"></td>\r\n<td colspan=\"4\" id=\"help3\" class=\"help\"></td>\r\n<td colspan=\"4\" id=\"help4\" class=\"help\"></td>\r\n<td colspan=\"2\" id=\"help5\" class=\"help\"></td>\r\n</tr>";
		eredmeny = eredmeny
				+ "<tr><td></td>\r\n<td colspan=\"2\" id=\"alert11\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert12\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert21\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert22\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert31\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert32\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert41\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert42\" class=\"alert\"></td>\r\n<td colspan=\"2\" id=\"alert51\" class=\"alert\"></td>\r\n</tr>";
		return eredmeny;
	}

	public String setDefaultButton() throws ServletException, IOException {
		String eredmeny = "	<form>\r\n"
				+ "		<input class=\"beosztasOKButton\"  type=\"button\" onclick=\"setDefault()\" value=\"Alapértelmezett\" />\r\n"
				+ "	</form>";
		return eredmeny;
	}

	public String alsoTabla() throws ServletException, IOException {
		String eredmeny = "";
		return eredmeny;
	}

	protected class Szemely {
		String csoport;
		String ID;
		String szajseb;
		String fogszabi;
		String inaktiv;

		void setCsoport(String valami) {
			// lehet DH, orvos, vagy asszisztens
			csoport = valami;
		}

		String getCsoport() {
			return csoport;
		}

		void setID(String IDout) {
			ID = IDout;
		}

		String getID() {
			return ID;
		}

		void setSzajseb(String szajsebOut) {
			szajseb = szajsebOut;
		}

		String getSzajseb() {
			return szajseb;
		}

		void setFogszabi(String fogszabiout) {
			fogszabi = fogszabiout;
		}

		String getFogszabi() {
			return fogszabi;
		}

		void setInaktiv(String inaktivout) {
			inaktiv = inaktivout;
		}

		String getInaktiv() {
			return inaktiv;
		}
	}
}
