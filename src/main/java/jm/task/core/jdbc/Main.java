package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;
public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Anya", "Shcherbedinskaya", (byte)28);
        userService.saveUser("Ivan", "Ivanov", (byte)7);
        userService.saveUser("Petr", "Petrov", (byte)48);
        userService.saveUser("Katerina", "Petrova", (byte)40);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
