package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSingleton {

	private volatile static ConnectionSingleton instance;
	private static Connection connection;
	private static Statement stmt;
	private static String url = "jdbc:postgresql://localhost:5432/Pizzaria Italian";
	private static String user = "postgres";
	private static String password = "ads";

	private ConnectionSingleton() {

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			stmt = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ConnectionSingleton getInstance() {

		if (instance == null) {
			synchronized (ConnectionSingleton.class) {
				if (instance == null) {
					instance = new ConnectionSingleton();
				}
			}
		}

		return instance;

	}

	public Statement getConnection() {
		return this.stmt;
	}

	public boolean isConnectionValid() {
		try {
			return this.connection.isValid(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void closeConnection() {

		try {

			if (connection != null) {
				stmt.close();
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
