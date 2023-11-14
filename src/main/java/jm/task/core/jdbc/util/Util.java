package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    // реализуйте настройку соеденения с БД

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/prep";
    private static final String USER = "root";
    private static final String PASS = "859929sql";
    private static SessionFactory sesFact = null;

    public static SessionFactory getSession() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER)
                    .setProperty("hibernate.connection.password", PASS)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                    .addAnnotatedClass(User.class)
                    .setProperty("hibernate.c3p0.min_size", "5")
                    .setProperty("hibernate.c3p0.max_size", "200")
                    .setProperty("hibernate.c3p0.max_statements", "200")
                    .setProperty("Environment.HBM2DDL_AUTO", "");

            ServiceRegistry servReg = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sesFact = configuration.buildSessionFactory(servReg);

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sesFact;
    }

    public static SessionFactory closeConnection() {
        sesFact.close();
        return null;
    }


    public static Connection getConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Подключение успешно");
        } catch (SQLException e) {
            System.out.println("Подключение не успешно");
        }
        return connection;
    }

}


