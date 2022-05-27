package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            String string = "CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(15)," +
                    "lastname VARCHAR(20), age INT)";
            session.createSQLQuery(string).addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch (HibernateException e) {
            System.out.println("Error while creating table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.getTransaction();
            transaction.begin();
            String string = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(string).addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch (HibernateException e) {
            System.out.println("Error while creating table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.getTransaction();
            transaction.begin();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        }catch (HibernateException e) {
            System.out.println("Error while creating table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.getTransaction();
            transaction.begin();
            session.delete(session.load(User.class, id));
            transaction.commit();
        }catch (HibernateException e) {
            System.out.println("Error while creating table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession();) {
            transaction = session.getTransaction();
            transaction.begin();
            users = session.createQuery("FROM User", User.class).getResultList();
            transaction.commit();
        }catch (HibernateException e) {
            System.out.println("Error while creating table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.getTransaction();
            transaction.begin();
            String sql = "DELETE FROM users";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch (HibernateException e) {
            System.out.println("Error while creating table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
