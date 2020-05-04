package service;

import dao.UserHibernateDAO;
import model.User;
import org.hibernate.SessionFactory;
import util.HibernateHelper;

import java.sql.SQLException;
import java.util.List;

public class UserHibernateService {
    private static UserHibernateService userHibernateService;
    private SessionFactory sessionFactory;
    private UserHibernateDAO userHibernateDAO = UserHibernateDAO.getInstance();

    private UserHibernateService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserHibernateService getInstance() {
        if (userHibernateService == null) {
            return new UserHibernateService(HibernateHelper.getHibernateSession());
        }
        return userHibernateService;
    }

    public void addUser(User user) throws SQLException {
        if (!isUserExist(user)) {
            userHibernateDAO.addUser(user);
        } else {
            System.out.println("Такой пользователь уже есть");
        }
    }

    public boolean isUserExist(User user) {
        return userHibernateDAO.isUserExist(user);
    }

    public List<User> getAllUsers() {
        return userHibernateDAO.getAllUsers();
    }


    public boolean deleteUser(long id) {
        return userHibernateDAO.deleteUser(id);
    }

    public void editUser(User user) {
        userHibernateDAO.editUser(user);
    }
}
