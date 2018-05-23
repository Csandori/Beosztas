import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

public class beosztObject {
	int id;
	int year;
	int week;
	int valami;
	String time;
	Date date;
	Calendar cal = Calendar.getInstance();
	Map<String, String> myMap = new HashMap<String, String>();
	Map<String, String> monthTime = new HashMap<String, String>();
	Map<String, String> monthMuszak = new HashMap<String, String>();

	beosztObject(String id) throws IOException, ServletException {

		// connecting to database
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM beosztasok WHERE id = \'" + id + "\'");
			ResultSetMetaData md = rs.getMetaData();
			while (rs.next()) {
				year = rs.getInt(2);
				week = rs.getInt(3);
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.WEEK_OF_YEAR, week);

				for (int i = 1; i < 333; i++) {
					myMap.put(md.getColumnName(i), rs.getString(i));
					myMap.put("valami", "valamivalue");
				}
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
		setMonthTimeandMuszak();
	}

	Map<String, String> returnMyMap() {
		return myMap;
	}

	void setMonthTimeandMuszak() throws IOException, ServletException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		for (int j = 0; j < 100; j++) {
			monthMuszak.put(Integer.toString(j), "0");
			monthTime.put(Integer.toString(j), "00:00");
		}
		try {

			sqlConnect connect = new sqlConnect();
			Class.forName(connect.driverName);
			con = DriverManager.getConnection(connect.connectDB, connect.connectUN, connect.connectP);
			stmt = con.createStatement();

			rs = stmt.executeQuery(
					"SELECT *, timediff(endTime,startTime) as difference FROM beosztasalap WHERE MONTH(date) = 5 and YEAR(date)="
							+ year + "");
			while (rs.next()) {
				time = rs.getString(11);
				for (int i = 5; i < 9; i++) {
					if (monthMuszak.get(rs.getString(i)) == null) {
						monthMuszak.put(rs.getString(i), "0");
					}
					if (monthTime.get(rs.getString(i)) == null) {
						monthTime.put(rs.getString(i), "00:00");
					}
					monthMuszak.put(rs.getString(i), add2IntString(monthMuszak.get(rs.getString(i)),"1"));
					monthTime.put(rs.getString(i), add2Time(monthTime.get(rs.getString(i)), time));
				}
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
	}

	String add2Time(String time1, String time2) {
		String timeEredmeny = "";
		String time1part[] = time1.split(":");
		String time2part[] = time2.split(":");
		if (Integer.parseInt(time1part[1]) + Integer.parseInt(time2part[1]) > 59) {
			if ((Integer.parseInt(time1part[0]) + Integer.parseInt(time2part[0]) + 1) < 10) {
				timeEredmeny = timeEredmeny + "0";
			}

			timeEredmeny = timeEredmeny + (Integer.parseInt(time1part[0]) + Integer.parseInt(time2part[0]) + 1) + ":";
			if ((Integer.parseInt(time1part[1]) + Integer.parseInt(time2part[1]) - 60) < 10) {
				timeEredmeny = timeEredmeny + "0";
			}
			timeEredmeny = timeEredmeny + (Integer.parseInt(time1part[1]) + Integer.parseInt(time2part[1]) - 60);

		} else {
			if ((Integer.parseInt(time1part[0]) + Integer.parseInt(time2part[0])) < 10) {
				timeEredmeny = timeEredmeny + "0";
			}

			timeEredmeny = timeEredmeny + (Integer.parseInt(time1part[0]) + Integer.parseInt(time2part[0])) + ":";
			if ((Integer.parseInt(time1part[1]) + Integer.parseInt(time2part[1])) < 10) {
				timeEredmeny = timeEredmeny + "0";
			}
			timeEredmeny = timeEredmeny + (Integer.parseInt(time1part[1]) + Integer.parseInt(time2part[1]));

		}
		return timeEredmeny;
	}

	String add2IntString(String string1, String string2) {
		return Integer.toString(Integer.parseInt(string1) + Integer.parseInt(string2));
	}
}
