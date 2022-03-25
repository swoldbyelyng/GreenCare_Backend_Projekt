package api.database.SQLDatabase;

import java.sql.*;

public class DBConnector {


    public Connection connectToLocalDB() {
        //localhost
        //3306
        String serverIP = "130.225.170.95:3306";
        String dbName = "backendTest";
        String url = "jdbc:mysql://locahost:3306/backendTest";
        String username = "admin3";
        String password = "abcdE123!";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public Connection connectToRemoteDB() {
        //localhost
        //3306
        String serverIP = "130.225.170.95:3306";
        String dbName = "backendTest";
        String url = "jdbc:mysql://130.225.170.95:3306/backendTest";
        String username = "admin3";
        String password = "abcdE123!";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return connection;
    }
}

