package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = HibernateUtil.getConnection();
    public UserDaoHibernateImpl() {
    }
    private Session getSession() {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        return session;
    }
    private void commitAndClose(Session session) {
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public void createUsersTable() {
        Session session = getSession();
        String string = "CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(15)," +
                "lastname VARCHAR(20), age INT)";
        session.createSQLQuery(string).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSession();
        String string = "DROP TABLE IF EXISTS users";
        session.createSQLQuery(string).addEntity(User.class).executeUpdate();
        commitAndClose(session);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSession();
        session.save(new User(name, lastName, age));
        commitAndClose(session);
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSession();
        session.delete(session.load(User.class, id));
        commitAndClose(session);
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSession();
        List<User> users = session.createQuery("from User", User.class).getResultList();
        commitAndClose(session);
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSession();
        session.createQuery("from User", User.class).stream().forEach(session::delete);
        commitAndClose(session);
    }
}
