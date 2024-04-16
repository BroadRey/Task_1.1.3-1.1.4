package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exceptions.*;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.validators.UserValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws CreateTableException {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY,"
                + "name VARCHAR(255),"
                + "last_name VARCHAR(255),"
                + "age SMALLINT"
                + ")";
        try (Connection connection = Util.getJDBCConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw (CreateTableException) new CreateTableException("Ошибка при создании таблицы пользователей!")
                    .initCause(e);
        }
    }

    public void dropUsersTable() throws DropTableException {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getJDBCConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw (DropTableException) new DropTableException("Ошибка при удалении таблицы пользователей!")
                    .initCause(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws DataSaveException, ValidationException {
        if (!UserValidator.isValidUser(name, lastName, age)) {
            throw new ValidationException("Невалидные данные пользователя!");
        }

        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw (DataSaveException) new DataSaveException("Ошибка при сохранении пользователя в таблицу!")
                    .initCause(e);
        }
    }

    public void removeUserById(long id) throws DataRemoveException, ValidationException {
        if (id < 0) {
            throw new ValidationException("Невалидный идентификатор пользователя!");
        }

        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw (DataRemoveException) new DataRemoveException("Ошибка при удалении пользователя из таблицы!")
                    .initCause(e);
        }
    }

    public List<User> getAllUsers() throws DataRetrievalException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = Util.getJDBCConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            throw (DataRetrievalException) new DataRetrievalException("Ошибка при извлечении пользователей из таблицы!")
                    .initCause(e);
        }
        return users;
    }


    public void cleanUsersTable() throws CleanTableException {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getJDBCConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw (CleanTableException) new CleanTableException("Ошибка при очистке таблицы пользователей!")
                    .initCause(e);
        }
    }
}
