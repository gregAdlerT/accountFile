package repositories.interfaces;

import models.User;

import java.util.List;

public interface IUserRepo {
    User getUserById(long id);
    List<User> getAllUsers();
    User addNewUser(User user);
    boolean removeUserById(long id);
    User updateUser(User user);
}
