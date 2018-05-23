
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Buttons
 */
@WebServlet("/beosztasTablazat")
public class beosztasTablazat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public beosztasTablazat() {
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
		Map<String, String> myMap = new HashMap<>();
		String printout = teljesTabla(request, response, myMap) + "<h1></h1>";
		printout = printout + tablaAlul() + "<h1></h1>";
		beosztasMethods hiddentable = new beosztasMethods();
		printout = printout + hiddentable.hiddenTable();
		response.getWriter().append(printout);

	}

	protected String elsoSor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Year = request.getParameter("Year");
		String Week = request.getParameter("Week");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.");
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(Week));
		cal.set(Calendar.YEAR, Integer.parseInt(Year));

		String eredmeny = "<h1></h1><form action=\"saveBeosztas.jsp\"><table id=\"beosztasTablazat\" style=\"width: 99%\" align=\"center\"> <tr>\r\n"
				+ "		<th border-style=\"none\"></th>\r\n"
				+ "		<th colspan=\"4\" class=\"jobbvastag balvastag topvastag\" id=\"date1\" name=\"date1\">"
				+ sdf.format(cal.getTime()) + "</th>\r\n" + "\r\n";
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		eredmeny = eredmeny + "		<th colspan=\"4\" id=\"date2\" class=\"jobbvastag topvastag\">"
				+ sdf.format(cal.getTime()) + "</th>\r\n";
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		eredmeny = eredmeny + "		<th colspan=\"4\" id=\"date3\" class=\"jobbvastag topvastag\">"
				+ sdf.format(cal.getTime()) + "</th>\r\n";
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		eredmeny = eredmeny + "		<th colspan=\"4\" id=\"date4\" class=\"jobbvastag topvastag\">"
				+ sdf.format(cal.getTime()) + "</th>\r\n";
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		eredmeny = eredmeny + "		<th colspan=\"2\" id=\"date5\" class=\"jobbvastag topvastag\">"
				+ sdf.format(cal.getTime()) + "</th>\r\n" + "\r\n"
				+ "	</tr><input type=\"hidden\" name=\"date10\" id=\"date10\" value=\"" + sdf.format(cal.getTime())
				+ "\"></input><input type=\"hidden\" name=\"topnavID\" value=\"3\"></input>";
		return eredmeny;
	}

	protected String masodikHarmadikSor() throws ServletException, IOException {
		String eredmeny = "<tr>\r\n" + "		<th></th>\r\n"
				+ "		<th colspan=\"4\" class=\"jobbvastag balvastag\">Hétfõ</th>\r\n"
				+ "		<th colspan=\"4\" class=\"jobbvastag\">Kedd</th>\r\n"
				+ "		<th colspan=\"4\" class=\"jobbvastag\">Szerda</th>\r\n"
				+ "		<th colspan=\"4\" class=\"jobbvastag\">Csütörtök</th>\r\n"
				+ "		<th colspan=\"2\" class=\"jobbvastag\">Péntek</th>\r\n" + "	</tr>\r\n" + "	<tr>\r\n"
				+ "		<th></th>\r\n" + "		<th colspan=\"2\" class=\"balvastag\">Délelõtt</th>\r\n"
				+ "		<th colspan=\"2\" class=\"jobbvastag\">Délután</th>\r\n"
				+ "		<th colspan=\"2\">Délelõtt</th>\r\n"
				+ "		<th colspan=\"2\" class=\"jobbvastag\">Délután</th>\r\n"
				+ "		<th colspan=\"2\">Délelõtt</th>\r\n"
				+ "		<th colspan=\"2\" class=\"jobbvastag\">Délután</th>\r\n"
				+ "		<th colspan=\"2\">Délelõtt</th>\r\n"
				+ "		<th colspan=\"2\" class=\"jobbvastag\">Délután</th>\r\n"
				+ "		<th colspan=\"2\" class=\"jobbvastag\">Délelõtt</th>\r\n" + "	</tr>";
		return eredmeny;
	}

	protected String teljesSor(int foMellek, int rendelo) throws ServletException, IOException {
		String eredmeny = new String();

		// td ID: rendelõ száma, elsõ vagy második sor, nap számozva, napszak 1 vagy 2,
		// orvos vagy asszisztens 1 vagy 2
		for (int i = 1; i < 6; i++) {
			int j = 1;
			for (; j < 3; j++) {
				String id = Integer.toString(rendelo) + foMellek + i + j;
				if (foMellek == 1) {
					eredmeny = eredmeny + "<td class=\"topvastag2\" ID=\"" + id + "1\"" + ">"
							+ selectMenu("", Integer.parseInt(id) * 10 + 1) + "</td>\r\n";

					eredmeny = eredmeny + "<td class=\"jobbvastag2 topvastag2\" ID=\"" + id + "2" + "\">"
							+ selectMenuAssz("", Integer.parseInt(id) * 10 + 2) + "</td>\r\n";
					if (i == 5) {
						break;
					}
				} else {
					if (i == 1 && j == 1) {
						eredmeny = eredmeny + "<tr>";
					}
					eredmeny = eredmeny + "<td class=\"valami\" ID=\"" + id + "1\"" + ">"
							+ selectMenu("", Integer.parseInt(id) * 10 + 1) + "</td>\r\n";

					eredmeny = eredmeny + "<td class=\"jobbvastag2\" ID=\"" + id + "2" + "\">"
							+ selectMenuAssz("", Integer.parseInt(id) * 10 + 2) + "</td>\r\n";
					if (i == 5) {
						break;
					}
				}

			}

		}
		eredmeny = eredmeny + "\r\n </tr>";
		return eredmeny;
	}

	protected String idopontok(int rendelo) throws ServletException, IOException {
		String eredmeny = new String();
		String startTime = new String();
		String endTime = new String();
		String id = "";
		// td ID: rendelõ száma, elsõ vagy második sor, nap számozva, napszak 1 vagy 2,
		// orvos vagy asszisztens 1 vagy 2
		for (int i = 1; i < 6; i++) {

			int j = 1;
			for (; j < 3; j++) {
				if (i == 1 && j == 1) {
					eredmeny = eredmeny + "<tr>";
				}
				if (j == 1) {
					startTime = "07:00";
					endTime = "14:30";
				} else {
					startTime = "14:00";
					endTime = "21:30";
				}
				id = Integer.toString(rendelo) + "3" + i + j;
				eredmeny = eredmeny + "<td class=\"timeInput\" id=\"" + id + "1\">\r\n<input name=\"" + id
						+ "10\" id=\"" + id + "10" + "\" type=\"time\" onchange=\"onChange(" + id + "10)\" value=\""
						+ startTime + "\"></td>\r\n";

				eredmeny = eredmeny + "<td class=\"timeInput jobbvastag2\" id=\"" + id + "2\">\r\n<input name=\"" + id
						+ "20\" id=\"" + id + "20" + "\" type=\"time\" onchange=\"onChange(" + id + "20)\" value=\""
						+ endTime + "\"></td>\r\n";
				if (i == 5) {
					break;

				}
			}
		}
		eredmeny = eredmeny + "\r\n </tr>";
		return eredmeny;
	}

	protected String teljesTabla(HttpServletRequest request, HttpServletResponse response, Map<String,String> myMap)
			throws ServletException, IOException {
		String eredmeny = elsoSor(request, response);
		eredmeny = eredmeny + masodikHarmadikSor();
		for (int i = 1; i < 7; i++) {
			if (i == 1) {
				eredmeny = eredmeny + "<tr>\r\n" + "		<th rowspan=\"3\" bgcolor=#FF8C00 class=\"rendelo\">1</th>";
			} else if (i == 2) {
				eredmeny = eredmeny + "<tr>\r\n" + "		<th rowspan=\"3\" bgcolor=#000080 class=\"rendelo\">2</th>";
			} else if (i == 3) {
				eredmeny = eredmeny + "<tr>\r\n" + "		<th rowspan=\"3\" bgcolor=#FFD700 class=\"rendelo\">3</th>";
			} else if (i == 4) {
				eredmeny = eredmeny + "<tr>\r\n" + "		<th rowspan=\"3\" bgcolor=#008000 class=\"rendelo\">4</th>";
			} else if (i == 5) {
				eredmeny = eredmeny + "<tr>\r\n" + "		<th rowspan=\"3\" bgcolor=#FFE4B5 class=\"rendelo\">5</th>";
			} else if (i == 6) {
				eredmeny = eredmeny + "<tr>\r\n" + "		<th rowspan=\"3\" bgcolor=#C0C0C0 class=\"rendelo\">6</th>";
			}
			eredmeny = eredmeny + teljesSor(1, i);
			eredmeny = eredmeny + teljesSor(2, i);
			eredmeny = eredmeny + idopontok(i);
			if (i == 6) {
				break;
			} else {
			}
		}

		beosztasMethods buttons = new beosztasMethods();
		eredmeny = eredmeny + buttons.textInputline(false, myMap);
		eredmeny = eredmeny + "	</table>\r\n" + "	<h1 id=\"pelda\"> </h1>\r\n";
		eredmeny = eredmeny + buttons.saveBeosztasButton();
		eredmeny = eredmeny + buttons.setDefaultButton();
		// eredmeny = eredmeny + script();
		return eredmeny;
	}

	protected String tablaAlul() throws ServletException, IOException {
		String[][] nevek = nameGetAssz();
		String eredmeny = "<table class=\"alap\" id=\"tablaAlul\">\r\n" + "		<tr>\r\n <td></td>";
		for (int i = 0; i < nevek.length; i++) {
			eredmeny = eredmeny + "<td id=\"tablaAlul" + nevek[i][1] + "\">" + nevek[i][0] + "</td>\r\n";
		}
		eredmeny = eredmeny + "</tr>";
		eredmeny = eredmeny + "<tr>\r\n <td>Heti mûszakszám</td>";
		for (int i = 0; i < nevek.length; i++) {
			eredmeny = eredmeny + "<td id=\"hetiMuszakszam" + nevek[i][1] + "\">0</td>\r\n";
		}
		eredmeny = eredmeny + "</tr>";
		eredmeny = eredmeny + "<tr>\r\n <td>Heti idõ</td>\r\n";
		for (int i = 0; i < nevek.length; i++) {
			eredmeny = eredmeny + "<td id=\"hetiIdo" + nevek[i][1] + "\">00:00</td>\r\n";
		}
		eredmeny = eredmeny + "</tr>";
		eredmeny = eredmeny + "<tr>\r\n <td>Havi mûszakszám</td>\r\n";
		eredmeny = eredmeny + "</tr>";

		eredmeny = eredmeny + "</table>\r\n <script>\r\n" + "      window.onload = function(){setEverything();}\r\n"
				+ " </script>";
		return eredmeny;
	}

	protected String script() throws ServletException, IOException {
		String eredmeny = new String("");
		eredmeny = "<script>\r\n" + "	function dateSetting() {\r\n" + "\r\n"
				+ "		var date = document.getElementById(\"date1\");\r\n" + "\r\n"
				+ "		var m = new Date(date.value);\r\n" + "		if (parseInt(m.getDay()) == 1) {\r\n"
				+ "			var num2 = new Date(m.getTime() + 86400000);\r\n"
				+ "			var num3 = new Date(m.getTime() + 86400000 * 2);\r\n"
				+ "			var num4 = new Date(m.getTime() + 86400000 * 3);\r\n"
				+ "			var num5 = new Date(m.getTime() + 86400000 * 4);\r\n"
				+ "			var stringDate2 = num2.getFullYear() + \".\" + (num2.getMonth() + 1)\r\n"
				+ "					+ \".\" + num2.getDate();\r\n"
				+ "			var stringDate3 = num3.getFullYear() + \".\" + (num3.getMonth() + 1)\r\n"
				+ "					+ \".\" + num3.getDate();\r\n"
				+ "			var stringDate4 = num4.getFullYear() + \".\" + (num4.getMonth() + 1)\r\n"
				+ "					+ \".\" + num4.getDate();\r\n"
				+ "			var stringDate5 = num5.getFullYear() + \".\" + (num5.getMonth() + 1)\r\n"
				+ "					+ \".\" + num5.getDate();\r\n" + "\r\n"
				+ "			document.getElementById(\"date2\").innerHTML = stringDate2;\r\n"
				+ "			document.getElementById(\"date3\").innerHTML = stringDate3;\r\n"
				+ "			document.getElementById(\"date4\").innerHTML = stringDate4;\r\n"
				+ "			document.getElementById(\"date5\").innerHTML = stringDate5;\r\n" + "		} else {\r\n"
				+ "			alert(\"Nem hétfõt adtál meg\");\r\n"
				+ "			document.getElementById(\"date2\").innerHTML = \"Keddi dátum\";\r\n"
				+ "			document.getElementById(\"date3\").innerHTML = \"Szerdai dátum\";\r\n"
				+ "			document.getElementById(\"date4\").innerHTML = \"Csütörtöki dátum\";\r\n"
				+ "			document.getElementById(\"date5\").innerHTML = \"Pénteki dátum\";\r\n" + "		}\r\n"
				+ "\r\n}\r\nfunction lathatovaTetel(ID) {\r\n"
				+ "					if (document.getElementById(ID).selectedIndex == \"0\") {\r\n"
				+ "						document.getElementById(id2).style.visibility = \"hidden\";\r\n"
				+ "					} else {\r\n" + "						var id2 = parseInt(ID) + 10000;\r\n"
				+ "						var id3 = parseInt(ID) / 10 - 1000;\r\n"
				+ "						var id4 = parseInt(ID) / 10 - 1000 + 1;\r\n"
				+ "						var id5 = parseInt(ID) / 10 - 1000 - 1;\r\n"
				+ "						document.getElementById(id2).style.visibility = \"visible\";\r\n"
				+ "						document.getElementById(id3).style.visibility = \"visible\";\r\n"
				+ "						document.getElementById(id4).style.visibility = \"visible\";\r\n"
				+ "						document.getElementById(id5).style.visibility = \"visible\";\r\n"
				+ "					}\r\n}</script>";
		return eredmeny;
	}

	protected String selectMenu(String elsoOpcio, int id) throws ServletException, IOException {
		// elsoOpció megadja, hogy mit jelenítsen meg alapértelmezettként
		String eredmeny = null;
		String[][] nevek = nameGet();

		if (Integer.toString(id).charAt(1) == '2') {
			eredmeny = "<select class=\"masod\" name = \"" + id + "0\" id=\"" + id + "0\" onchange=\"onChange(\'" + id
					+ "0\')\">\r\n<option value=\"0\">" + elsoOpcio + "</option>\r\n";
		} else {
			eredmeny = "<select name = \"" + id + "0\" id=\"" + id + "0\" onchange=\"onChange(\'" + id
					+ "0\')\">\r\n<option value=\"0\">" + elsoOpcio + "</option>\r\n";
		}
		for (int i = 0; i < nevek.length; i++) {
			eredmeny = eredmeny + "<option value=\"" + nevek[i][1] + "\">" + nevek[i][0] + "</option>\r\n";
		}
		eredmeny = eredmeny + "</select>";
		return eredmeny;
	}

	protected String selectMenuAssz(String elsoOpcio, int id) throws ServletException, IOException {
		// elsoOpció megadja, hogy mit jelenítsen meg alapértelmezettként
		String eredmeny = null;
		String[][] nevek = nameGetAssz();

		if (Integer.toString(id).charAt(1) == '2') {
			eredmeny = "<select class=\"masod\" name = \"" + id + "0\" id=\"" + id + "0\" onchange=\"onChange(\'" + id
					+ "0\')\">\r\n<option value=\"0\">" + elsoOpcio + "</option>\r\n";
		} else {
			eredmeny = "<select name = \"" + id + "0\" id=\"" + id + "0\" onchange=\"onChange(\'" + id
					+ "0\')\">\r\n<option value=\"0\">" + elsoOpcio + "</option>\r\n";
		}
		for (int i = 0; i < nevek.length; i++) {
			eredmeny = eredmeny + "<option value=\"" + nevek[i][1] + "\">" + nevek[i][0] + "</option>\r\n";
		}
		eredmeny = eredmeny + "</select>";
		return eredmeny;
	}

	protected String[][] nameGet() throws ServletException, IOException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		int countID = 0;
		int result = 0;
		String oda = new String();
		try {
			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM employee where inaktiv=0");
			while (rs.next()) {
				oda = rs.getObject(6).toString();
				if (oda.contains("o") || oda.contains("d")) {
					result++;
				}
			}

			String[][] name = new String[result][result];
			Szemely[] szemelyek = new Szemely[result];
			String value = new String();
			rs.close();
			rs = null;
			rs = stmt.executeQuery("SELECT * FROM employee where inaktiv=0");

			while (rs.next()) {

				oda = rs.getObject(6).toString();
				if (oda.contains("o")) {
					szemelyek[countID] = new Szemely();
					szemelyek[countID].setCsoport(rs.getObject(6).toString());
					szemelyek[countID].setID(rs.getObject(1).toString());
					szemelyek[countID].setSzajseb(rs.getObject(3).toString());
					szemelyek[countID].setFogszabi(rs.getObject(4).toString());
					szemelyek[countID].setInaktiv(rs.getObject(5).toString());
					value = szemelyek[countID].getID();
					name[countID][1] = value;
					value = null;
					name[countID][0] = rs.getObject(2).toString();
					countID++;
				} else if (oda.contains("d")) {
					szemelyek[countID] = new Szemely();
					szemelyek[countID].setCsoport(rs.getObject(6).toString());
					szemelyek[countID].setID(rs.getObject(1).toString());
					szemelyek[countID].setSzajseb(rs.getObject(3).toString());
					szemelyek[countID].setFogszabi(rs.getObject(4).toString());
					szemelyek[countID].setInaktiv(rs.getObject(5).toString());
					value = szemelyek[countID].getID();
					name[countID][1] = value;
					value = null;
					name[countID][0] = rs.getObject(2).toString() + " DH";
					countID++;
				}
			}

			// sorba állítja az opciókat
			Arrays.sort(name, (String[] s1, String[] s2) -> s1[0].compareTo(s2[0]));
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

	protected String[][] nameGetAssz() throws ServletException, IOException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		int countID = 0;
		int result = 0;
		String oda = new String();
		try {
			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM employee where inaktiv=0");
			while (rs.next()) {
				oda = rs.getObject(6).toString();
				if (oda.contains("a") || oda.contains("d")) {
					result++;
				}
			}

			String[][] name = new String[result][result];
			Szemely[] szemelyek = new Szemely[result];
			String value = new String();
			rs.close();
			rs = null;
			rs = stmt.executeQuery("SELECT * FROM employee where inaktiv=0");

			while (rs.next()) {

				oda = rs.getObject(6).toString();
				if (oda.contains("a") || oda.contains("d")) {
					szemelyek[countID] = new Szemely();
					szemelyek[countID].setCsoport(rs.getObject(6).toString());
					szemelyek[countID].setID(rs.getObject(1).toString());
					szemelyek[countID].setSzajseb(rs.getObject(3).toString());
					szemelyek[countID].setFogszabi(rs.getObject(4).toString());
					szemelyek[countID].setInaktiv(rs.getObject(5).toString());
					value = szemelyek[countID].getID();
					name[countID][1] = value;
					value = null;
					name[countID][0] = rs.getObject(2).toString();
					countID++;
				}
			}

			// sorba állítja az opciókat
			Arrays.sort(name, (String[] s1, String[] s2) -> s1[0].compareTo(s2[0]));
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
