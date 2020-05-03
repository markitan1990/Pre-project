package util;

import com.mysql.jdbc.Driver;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBhelper  {
    private static Connection connection;
//    private static SessionFactory sessionFactory;

    private DBhelper() {

    }

    public static Connection getJdbcConnection() {
        if (connection == null) {
            return new DBhelper().getConnection();
        }
        return connection;
    }

//    public static SessionFactory getHibernateSession() {
//        if (sessionFactory == null) {
//            return new DBhelper().getSessionFactory();
//        }
//        return sessionFactory;
//    }

//    public SessionFactory getSessionFactory() {
//
//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(User.class);
//
//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/users");
//        configuration.setProperty("hibernate.connection.username", "root");
//        configuration.setProperty("hibernate.connection.password", "root");
//        configuration.setProperty("hibernate.show_sql", "true");
//        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
//
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
//        builder.applySettings(configuration.getProperties());
//        ServiceRegistry serviceRegistry = builder.build();
//
//        return configuration.buildSessionFactory(serviceRegistry);
//
//    }

    public Connection getConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("users?").          //db name
                    append("user=root&").          //login
                    append("password=root&").       //password
                    append("serverTimezone=UTC");   //setup server time

            connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
