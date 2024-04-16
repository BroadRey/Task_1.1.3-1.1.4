package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exceptions.*;
import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTable() throws CreateTableException;

    void dropUsersTable() throws DropTableException;

    void saveUser(String name, String lastName, byte age) throws DataSaveException, ValidationException;

    void removeUserById(long id) throws DataRemoveException, ValidationException;

    List<User> getAllUsers() throws DataRetrievalException;

    void cleanUsersTable() throws CleanTableException;
}
