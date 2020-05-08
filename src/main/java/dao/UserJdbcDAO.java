package dao;


import model.User;
import org.w3c.dom.ls.LSOutput;
import util.DBhelper;
import util.UserDao;

import javax.management.Query;
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
                     connection.prepareStatement("insert into users (name, lastName, password, role) values  (?,?,?,?)")) {
            try {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getRole());
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
                     connection.prepareStatement("select * from users where name = ? and lastName = ? and password = ?")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if ( resultSet.next()){
                res = true;
            }
        } catch (SQLException e) {
            System.out.println("Не верный запрос");
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
                String pass = resultSet.getString("password");
                String role = resultSet.getString("role");
                list.add(new User(id, name, lastName, pass, role));
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ?, lastName = ?, password = ?, role = ? where id = ?")) {
            connection.setAutoCommit(false);
            try {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getRole());
                preparedStatement.setLong(5, user.getId());
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

    @Override
    public User getUser(User user) {
        User res = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from users where name = ? and lastName = ? and password = ?")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            res = new User(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastName"),
                    resultSet.getString("password"),
                    resultSet.getString("role"));
        } catch (SQLException e) {
            System.out.println("Не правельный запрос");
        }
        return res;
    }
}
