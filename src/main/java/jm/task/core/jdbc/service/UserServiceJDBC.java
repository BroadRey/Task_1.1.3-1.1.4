package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.exceptions.*;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceJDBC implements UserService {

    public void createUsersTable() {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        try {
            userDao.createUsersTable();
            System.out.println("Таблица успешно создана!");

        } catch (CreateTableException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        try {
            userDao.dropUsersTable();
            System.out.println("Таблица успешно удалена!");
        } catch (DropTableException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        try {
            userDao.saveUser(name, lastName, age);
            System.out.println("Пользователь успешно сохранен!");
        } catch (DataSaveException | ValidationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        try {
            userDao.removeUserById(id);
            System.out.println("Пользователь успешно удален!");
        } catch (DataRemoveException | ValidationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        try {
            List<User> allUsers = userDao.getAllUsers();
            System.out.println("Пользователи успешно извлечены!");
            return allUsers;
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void cleanUsersTable() {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        try {
            userDao.cleanUsersTable();
            System.out.println("Таблица успешно очищена!");
        } catch (CleanTableException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
