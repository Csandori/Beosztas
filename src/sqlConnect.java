

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class sqlConnect
 */
@WebServlet("/sqlConnect")
public class sqlConnect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String connectDB = "jdbc:mysql://localhost:3306/beosztas?useSSL=false";
	String connectUN = "root";
	String connectP = "password";
	String driverName = "com.mysql.jdbc.Driver";
}
