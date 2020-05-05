package util;

import dao.UserHibernateDAO;
import dao.UserJdbcDAO;

public class UserDaoFactory {
    public UserDao getCurentConnection(String type) {
        switch (type) {
            case "hibernate":
                return UserHibernateDAO.getInstance();
            case "jdbc":
                return UserJdbcDAO.getIstance();
        }
        return UserHibernateDAO.getInstance();
    }
}
