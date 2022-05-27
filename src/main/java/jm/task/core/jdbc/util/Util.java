package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/kata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Eve02041991";
    private static SessionFactory sessionFactory = null;
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Log in base successfully");
        } catch (Exception e) {
            System.out.println("Error while log in base");
            e.printStackTrace();
        }
        return connection;
    }
    public static SessionFactory getSessionFactory() {
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.connection.url",URL);
        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        cfg.setProperty("hibernate.connection.username", USERNAME);
        cfg.setProperty("hibernate.connection.password", PASSWORD);
        cfg.setProperty("current_session_context_class", "thread");
        cfg.setProperty("hibernate.connection.dialect", "org.hibernate.dialect.MySQL8Dialect");
        cfg.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
        try {
            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(cfg.getStandardServiceRegistryBuilder().getBootstrapServiceRegistry());
        }
        return sessionFactory;
    }
}

