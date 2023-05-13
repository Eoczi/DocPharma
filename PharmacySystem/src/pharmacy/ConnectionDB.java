package pharmacy;

import java.sql.*;

public class ConnectionDB {
	private static final String URL = "jdbc:mysql://localhost:3306/doc_farma";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "edik";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
    
    
}
