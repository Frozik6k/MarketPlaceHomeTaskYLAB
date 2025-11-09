package repository;

import model.User;

import java.util.Optional;

public interface UserRepository {
    void createUser(User user);

    void updateUser(User user);

    Optional<User> getuser(String username);
}
