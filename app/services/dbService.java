package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbService {
    private static dbService instance;
    private Connection connection = null;

    private dbService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String ip = "10.110.10.32:3306";
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + ip + "/ecommerce?user=root&password=formation0&useSSL=false");
        } catch (ClassNotFoundException | SQLException e) {
            System.exit(0);
        }
    }

    public static dbService get() {
        if (instance == null) {
            instance = new dbService();
        }
        return instance;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
