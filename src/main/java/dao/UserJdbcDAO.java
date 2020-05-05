package dao;


import model.User;
import org.w3c.dom.ls.LSOutput;
import util.DBhelper;
import util.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAO implements UserDao {
    private static UserJdbcDAO userJdbcDAO;
    private Connection connection;

    private UserJdbcDAO(Connection connection) {
        this.connection = connection;
    }

    public static UserJdbcDAO getIstance() {
        if (userJdbcDAO == null) {
            return new UserJdbcDAO(DBhelper.getInstance().getConnection());
        }
        return userJdbcDAO;
    }

    @Override
    public void addUser(User user) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("insert into users (name, lastName, age) values  (?,?,?)")) {
            try {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setLong(3, user.getAge());
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException throwables) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException throwables) {
            System.out.println("Не получилось добавить пользователя");
        }
    }

    @Override
    public boolean isUserExist(User user) {
        boolean res = false;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("select exists (select 1  from users where name = ? and lastName = ? and age = ? )")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, user.getAge());
            res = preparedStatement.execute();
        } catch (SQLException e) {

        }
        return res;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                long age = resultSet.getLong("age");
                list.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e) {
            System.out.println("Список не получен");
        }
        return list;
    }


    @Override
    public boolean deleteUser(long id) {
        boolean res = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?")) {
            preparedStatement.setLong(1, id);
            connection.setAutoCommit(false);
            try {
                preparedStatement.execute();
                connection.commit();
                res = true;
            } catch (SQLException throwables) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException throwables) {
            System.out.println("Пользователь не удален");
        }
        return res;
    }

    @Override
    public void editUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ?, lastName = ?, age = ? where id = ?")) {
            connection.setAutoCommit(false);
            try {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setLong(3, user.getAge());
                preparedStatement.setLong(4, user.getId());
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException throwables) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException throwables) {
            System.out.println("Не удалось редактирование");
        }
    }
}
