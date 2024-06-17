package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static String url = "jdbc:mysql://127.0.0.1:3306/healthcare";
    private static String user = "root";
    private static String password = "w123i456n7890";

    public static void changeConnection(String newurl) {
        url = newurl;
    }

    public static void changeUser(String newuser) {
        user = newuser;
    }

    public static void changePassword(String userpassword) {
        password = userpassword;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
