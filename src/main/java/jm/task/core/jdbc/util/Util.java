package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getConnection() {
        return getConnection("jdbc:mysql://localhost:3306", "root", "root");
    }
    public static Connection getConnection(String user, String password) {
        return getConnection("jdbc:mysql://localhost:3306", user, password);
    }
    public static Connection getConnection(String adress, String user, String password) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(adress, user, password);
            System.out.println("Log in base successfully");
        } catch (Exception e) {
            System.out.println("Error while log in base");
            e.printStackTrace();
        }
        return connection;
    }
}

