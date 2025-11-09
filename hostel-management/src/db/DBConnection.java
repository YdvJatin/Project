package db;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:mysql://bytexldb.com:5051/db_443nt3j93?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "user_443nt3j93";
    private static final String PASS = "p443nt3j93";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
