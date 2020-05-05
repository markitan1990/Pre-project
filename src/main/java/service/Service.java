package service;

import model.User;
import util.UserDao;
import util.UserDaoFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


public class Service {
    private static Service service;
    private UserDao userDao;

    private Service(UserDao userDao) {
        this.userDao = userDao;
    }

    public static Service getInstance() {
        if (service == null) {
            return new Service(new UserDaoFactory().getCurentConnection(getProperties()));
        }
        return service;
    }

    public static String getProperties() {
        Properties properties = new Properties();
        String daotype = "";
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String appConfigPath = rootPath + "config.properties";

            FileInputStream fileInputStream = new FileInputStream(appConfigPath);
            properties.load(fileInputStream);
            daotype = properties.getProperty("daotype");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return daotype;
    }

    public void addUser(User user) throws SQLException {
        if (!isUserExist(user)) {
            userDao.addUser(user);
        } else {
            System.out.println("Такой пользователь уже есть");
        }
    }

    public boolean isUserExist(User user) {
        return userDao.isUserExist(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


    public boolean deleteUser(long id) {
        return userDao.deleteUser(id);
    }

    public void editUser(User user) {
        userDao.editUser(user);
    }
}
