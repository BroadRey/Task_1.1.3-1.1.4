package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceJDBC;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceJDBC();
        userService.createUsersTable();
        userService.saveUser("Andrey", "Andreev", (byte) 24);
        userService.saveUser("Igor", "Igorev", (byte) 47);
        userService.saveUser("Petr", "Petrov", (byte) 50);
        userService.saveUser("Oleg", "Olegov", (byte) 11);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
