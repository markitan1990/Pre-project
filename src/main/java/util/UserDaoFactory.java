package util;

import dao.UserDao;
import dao.UserHibernateDAO;
import dao.UserJdbcDAO;

public class UserDaoFactory {
    private DBhelper dBhelper = new DBhelper();

    public UserDao getDAO(String type) {
        switch (type) {
            case "hibernate":
                return new UserHibernateDAO(dBhelper.getConfiguration());
            case "jdbc":
                return new UserJdbcDAO(dBhelper.getConnection());
        }
        return new UserHibernateDAO(dBhelper.getConfiguration());
    }
}
