package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.boot.registry.internal.BootstrapServiceRegistryImpl;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/kata");
        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        cfg.setProperty("hibernate.connection.username", "root");
        cfg.setProperty("hibernate.connection.password", "root");
        cfg.setProperty("current_session_context_class", "thread");
        cfg.setProperty("hibernate.connection.dialect", "org.hibernate.dialect.MySQL8Dialect");
        cfg.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

        try {
            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(cfg.getStandardServiceRegistryBuilder().getBootstrapServiceRegistry());
        }
    }
    public static SessionFactory getConnection() {
        return sessionFactory;
    }


}
