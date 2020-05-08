package util;

import model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    boolean isUserExist(User user);
    List<User> getAllUsers();
    boolean deleteUser(long id);
    void editUser(User user);
    User getUser(User user);
}
