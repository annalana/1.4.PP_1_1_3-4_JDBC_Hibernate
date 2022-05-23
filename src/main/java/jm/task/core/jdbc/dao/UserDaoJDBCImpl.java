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
    public UserDaoJDBCImpl(){
        connection = Util.getConnection();
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Error while creating statement");
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
        try {
            String a = String.format("INSERT INTO kata.userstable (username, lastname, age) VALUES ('%s', '%s', %d)",
                    name, lastName, age);
            connection.prepareStatement(a).executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            System.out.println("Error when saving user");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            connection.prepareStatement(String.format("DELETE FROM kata.userstable WHERE id=%d", id)).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error when removing user");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User newUser;
        try {
            ResultSet users = connection.prepareStatement("SELECT * from kata.userstable").executeQuery();
            while (users.next()) {
                users.getInt("id");
                newUser = new User(users.getString("username"), users.getString("lastname"),
                        (byte)(users.getInt("age")));
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
