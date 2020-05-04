package service;


import dao.UserJdbcDAO;
import model.User;
import util.DBhelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserJdbcService {
    private static UserJdbcService userJdbcService;
    private Connection connection;
    private UserJdbcDAO userJdbcDAO = UserJdbcDAO.getIstance();

    private UserJdbcService(Connection connection) {
        this.connection = connection;
    }

    public static UserJdbcService getInstance() {
        if (userJdbcService == null) {
            return new UserJdbcService(DBhelper.getJdbcConnection());
        }
        return userJdbcService;
    }

    public void addUser(User user) throws SQLException {
        if (!isUserExist(user)) {
            userJdbcDAO.addUser(user);
        } else {
            System.out.println("Такой пользователь уже есть");
        }
    }

    public boolean isUserExist(User user) {
        boolean res = false;
        try {
            res = userJdbcDAO.findUser(user);
        } catch (SQLException throwables) {
            System.out.println("Что-то пошло не так");
        }
        return res;
    }

    public List<User> getAllUsers() {
        List<User> list = null;
        try {
            list = userJdbcDAO.getListUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteUser(long id) {
        boolean res = false;
        try {
            res = userJdbcDAO.deleteUser(id);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя");
        }
        return res;
    }

    public void editUser(User user) {
        try {
            userJdbcDAO.editUser(user);
        } catch (SQLException throwables) {
            System.out.println("Не удалось обновить пользователя");
        }
    }
}
