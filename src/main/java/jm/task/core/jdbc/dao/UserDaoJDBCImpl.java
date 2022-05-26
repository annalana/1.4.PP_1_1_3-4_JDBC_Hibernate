package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
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
            statement.execute("DROP TABLE kata.users");
        } catch(SQLSyntaxErrorException e) {
            System.out.println("Table is not existing or something went wrong");
        }catch (Exception e) {
            System.out.println("Error when dropping table");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String sql = "INSERT INTO kata.users (username, lastname, age) VALUES (?, ?, ?)";
            PreparedStatement sqlStatement = connection.prepareStatement(sql);
            sqlStatement.setString(1, name);
            sqlStatement.setString(2, lastName);
            sqlStatement.setInt(3, age);
            sqlStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            System.out.println("Error when saving user");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            String sql = "DELETE FROM kata.users WHERE id=?";
            PreparedStatement sqlStatement = connection.prepareStatement(sql);
            sqlStatement.setLong(1, id);
            sqlStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error when removing user");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User newUser;
        try {
            String sql = "SELECT * from kata.users";

            ResultSet users = connection.prepareStatement(sql).executeQuery();
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
            statement.execute("DELETE from kata.users");
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
