package connectDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	  
	 public  static Connection con = null;
	 private static Database instance = new Database();
	 public static Database getInstance() {
			return instance;
		}
	public void connect() {
		String url = "jdbc:sqlserver://localhost:1433;databasename=QLLop";
		String user = "sa";
		String password = "Abc12345";
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		return con;
	}
	public void disconnect() {
		if(con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
