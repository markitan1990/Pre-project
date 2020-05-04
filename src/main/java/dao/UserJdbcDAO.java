package dao;


import model.User;
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
            return new UserJdbcDAO(DBhelper.getJdbcConnection());
        }
        return userJdbcDAO;
    }

    public void addUser(User user) throws SQLException {
        try (Statement statement = connection.createStatement();
             PreparedStatement preparedStatement =
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
        }
    }

    public boolean findUser(User user) throws SQLException {
        boolean res = false;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("select exists (select 1  from users where name = ? and lastName = ? and age = ? )")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, user.getAge());
            res = preparedStatement.execute();
        }
        return res;
    }

    public List<User> getListUsers() throws SQLException {
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
        }
        return list;
    }

    public boolean deleteUser(long id) throws SQLException {
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
            }
        }finally {
            connection.setAutoCommit(true);
        }
        return res;
    }

    public void editUser(User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ?, lastName = ?, age = ? where id = ?")){
            connection.setAutoCommit(false);
            try {
                preparedStatement.setString(1,user.getName());
                preparedStatement.setString(2,user.getLastName());
                preparedStatement.setLong(3, user.getAge());
                preparedStatement.setLong(4, user.getId());
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException throwables) {
                connection.rollback();
            }
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
