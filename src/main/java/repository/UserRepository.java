package repository;

import model.User;

public interface UserRepository {
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getuser(String username);
}
