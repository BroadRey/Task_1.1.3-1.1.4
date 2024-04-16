package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.exceptions.*;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceHibernate implements UserService {

    public void createUsersTable() {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        try {
            userDaoHibernate.createUsersTable();
            System.out.println("Таблица успешно создана!");
        } catch (CreateTableException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        try {
            userDaoHibernate.dropUsersTable();
            System.out.println("Таблица успешно удалена!");
        } catch (DropTableException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        try {
            userDaoHibernate.saveUser(name, lastName, age);
            System.out.println("Пользователь успешно сохранен!");
        } catch (DataSaveException | ValidationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        try {
            userDaoHibernate.removeUserById(id);
            System.out.println("Пользователь успешно удален!");
        } catch (DataRemoveException | ValidationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        try {
            List<User> allUsers = userDaoHibernate.getAllUsers();
            System.out.println("Пользователи успешно извлечены!");
            return allUsers;
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void cleanUsersTable() {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        try {
            userDaoHibernate.cleanUsersTable();
            System.out.println("Таблица успешно очищена!");
        } catch (CleanTableException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
