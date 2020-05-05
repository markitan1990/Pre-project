package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.DBhelper;
import util.UserDao;

import java.util.List;

public class UserHibernateDAO implements UserDao {
    private static UserHibernateDAO userHibernateDAO;
    private SessionFactory sessionFactory;

    private UserHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserHibernateDAO getInstance() {
        if (userHibernateDAO == null) {
            return new UserHibernateDAO(DBhelper.getInstance().getConfiguration());
        }
        return userHibernateDAO;
    }

    @Override
    public boolean isUserExist(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from User where name = :name and lastName = :lastName and age =:age");
            query.setParameter("name", user.getName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("age", user.getAge());
            List<User> list = query.list();
            if (list.size() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from User");
            list = query.list();
        }
        return list;
    }

    @Override
    public boolean deleteUser(long id) {
        boolean res = false;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete((User) session.load(User.class, id));
                transaction.commit();
                res = true;
            } catch (Exception e) {
                transaction.commit();
            }
        }
        return res;
    }

    @Override
    public void editUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
}
