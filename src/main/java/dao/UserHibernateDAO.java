package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserHibernateDAO implements UserDao {
    private SessionFactory sessionFactory;

    public UserHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public boolean isUserExist(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from User where name = :name and lastName = :lastName and password =:password");
            query.setParameter("name", user.getName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("password", user.getPassword());
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

    @Override
    public User getUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from User where name = :name and lastName = :lastName and password = :password");
            query.setParameter("name", user.getName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("password", user.getPassword());

            List<User> list = query.list();
            return list.get(0);
        }
    }

    @Override
    public User getUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }
}
