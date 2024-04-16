package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exceptions.*;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.validators.UserValidator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Objects;

public class UserDaoHibernateImpl implements UserDao {
    private final Configuration configuration;

    public UserDaoHibernateImpl() {
        configuration = Util.getHibernateConfiguration();
    }


    @Override
    public void createUsersTable() throws CreateTableException {
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("""
                    CREATE TABLE IF NOT EXISTS users (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        last_name VARCHAR(255),
                        age SMALLINT
                    );
                    """).executeUpdate();
            session.getTransaction()
                    .commit();
        } catch (HibernateException e) {
            throw (CreateTableException) new CreateTableException("Ошибка при создании таблицы пользователей!")
                    .initCause(e);
        }
    }


    @Override
    public void dropUsersTable() throws DropTableException {
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        } catch (HibernateException e) {
            throw (DropTableException) new DropTableException("Ошибка при удалении таблицы пользователей!")
                    .initCause(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws DataSaveException, ValidationException {
        if (!UserValidator.isValidUser(name, lastName, age)) {
            throw new ValidationException("Невалидные данные пользователя!");
        }

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction()
                    .commit();
        } catch (HibernateException e) {
            throw (DataSaveException) new DataSaveException("Ошибка при сохранении пользователя в таблицу!")
                    .initCause(e);
        }
    }

    @Override
    public void removeUserById(long id) throws DataRemoveException, ValidationException {
        if (id < 0) {
            throw new ValidationException("Невалидный идентификатор пользователя!");
        }

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (Objects.nonNull(user)) {
                session.remove(user);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw (DataRemoveException) new DataRemoveException("Ошибка при удалении пользователя из таблицы!")
                    .initCause(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DataRetrievalException {
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User")
                    .getResultList();
            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            throw (DataRetrievalException) new DataRetrievalException(
                    "Ошибка при извлечении всех пользователей из таблицы!"
            ).initCause(e);
        }
    }

    @Override
    public void cleanUsersTable() throws CleanTableException {
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw (CleanTableException) new CleanTableException("Ошибка при очистке таблицы пользователей!")
                    .initCause(e);
        }
    }
}
