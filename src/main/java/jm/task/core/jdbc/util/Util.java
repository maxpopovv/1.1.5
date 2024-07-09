//package jm.task.core.jdbc.util;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class Util {
//    private static Util instance;
//    private static DataSource dataSource;
//    private static Connection connection;
//
//    private Util() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:mysql://localhost:3306/new");
//        config.setUsername("root");
//        config.setPassword("root");
//
//        dataSource = new HikariDataSource(config);
//    }
//
//    public static Util getInstance() {
//        if (instance == null) {
//            synchronized (Util.class) {
//                if (instance == null) {
//                    instance = new Util();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public static Connection getConnection() throws SQLException {
//        if (connection == null || connection.isClosed()) {
//            connection = dataSource.getConnection();
//        }
//        return connection;
//    }
//}


package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // по сути хибернет юзает jdbc просто не так явно как мы в 1.1.4 делали, это его настройки
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/new");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "root");

            // объявляю диалект MySQL
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

            // отобржание SQL кода в консоли
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            // обновляет базу при запуске приложения
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // в общем я тут использую соединение С3P0 и как я понял это параметры, с хикари чет не получается приконнектиться.
            configuration.setProperty("hibernate.c3p0.min_size", "5");
            configuration.setProperty("hibernate.c3p0.max_size", "20");
            configuration.setProperty("hibernate.c3p0.timeout", "300");
            configuration.setProperty("hibernate.c3p0.max_statements", "50");
            configuration.setProperty("hibernate.c3p0.idle_test_period", "3000");

            // присоедният аннотированный класс User
            configuration.addAnnotatedClass(User.class);

            // строим фабрику сессий
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}