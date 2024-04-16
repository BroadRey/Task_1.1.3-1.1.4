package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/test";

    public static Connection getJDBCConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static Configuration getHibernateConfiguration() {
        return new Configuration()
                .addAnnotatedClass(User.class);
    }
}
