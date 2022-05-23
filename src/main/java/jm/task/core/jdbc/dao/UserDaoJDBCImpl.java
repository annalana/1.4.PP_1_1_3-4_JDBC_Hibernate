package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;
    private Statement statement;
    public UserDaoJDBCImpl() {
        this(Util.getConnection());
    }
    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Error while creating statement");
            e.printStackTrace();
        }
    }
    public void createUsersTable()  {
        try {
            statement.executeUpdate("CREATE TABLE kata.userstable (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(20), lastName VARCHAR(20), age INT)");
        } catch(SQLSyntaxErrorException e) {
            System.out.println("Table is already existing or something went wrong");
        }catch (Exception e) {
            System.out.println("Error when creating table");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute("DROP TABLE kata.userstable");
        } catch(SQLSyntaxErrorException e) {
            System.out.println("Table is not existing or something went wrong");
        }catch (Exception e) {
            System.out.println("Error when dropping table");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        StringBuilder userData = new StringBuilder("INSERT INTO kata.userstable (username, lastname, age) VALUES ('")
                .append(name).append("', '"). append(lastName).append("', ").append(age).append(")");
        try {
            statement.executeUpdate(userData.toString());
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            System.out.println("Error when saving user");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        StringBuilder userData = new StringBuilder("DELETE FROM kata.userstable WHERE id=").append(id);
        try {
            statement.executeUpdate(userData.toString());
        } catch (Exception e) {
            System.out.println("Error when removing user");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User newUser;
        try {
            ResultSet users = statement.executeQuery("SELECT * from kata.userstable");
            while (users.next()) {
                users.getInt("id");
                newUser = new User(users.getString("username"), users.getString("lastname"), (byte)(users.getInt("age")));
                newUser.setId(users.getLong("id"));
                userList.add(newUser);
            }
        } catch (Exception e) {
            System.out.println("Error when getting userslist");
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            statement.execute("DELETE from kata.userstable");
        } catch (Exception e) {
            System.out.println("Error when getting cleaning table");
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            connection.close();
            System.out.println("Database connection closed");
        }catch (Exception e) {
            System.out.println("Ошибка при закрытии базы");
        }
    }
}
