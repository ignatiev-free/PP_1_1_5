package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    public static Connection getConnection() {

        String dbUrl = "jdbc:mysql://localhost:3306/prep";
        String user = "root";
        String password = "859929sql";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("Подключение успешно");
        } catch (SQLException e) {
            System.out.println("Подключение не успешно");
        }
        return connection;
    }

}
