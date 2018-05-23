import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

public class sqlParancsok {
	String dateYear;
	String dateWeek;
	Date dateofMonday;
	sqlParancsok(String id) throws IOException, ServletException {

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
			while (rs.next()) {
				dateYear = rs.getString(2);
				dateWeek = rs.getString(3);
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

	String haviOraszam(String dateHetfo, String datePentek, int id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.");
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(dateWeek));
		cal.set(Calendar.YEAR, Integer.parseInt(dateYear));
		
		String eredmeny = "SELECT * FROM beosztas.beosztasalap WHERE date<'" + dateHetfo + "' or date>'" + datePentek
				+ "' and (prime1='" + id + "' or prime2='" + id + "' or assistant1='" + id + "' or assistant2='" + id
				+ "');";
		return eredmeny;
	}

	String haviMuszakszam(String dateHetfo, String datePentek, int id) {
		String eredmeny = "SELECT * FROM beosztas.beosztasalap WHERE date<'2018.05.24' and date>'2018.05.22' and rendelo='2';";
		return eredmeny;
	}
}
