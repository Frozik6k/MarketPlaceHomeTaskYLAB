package repository.impl;

import model.User;
import repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    Map<String, User> users = new HashMap<>();

    @Override
    public void createUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Такой пользователь уже существует");
        }
        users.put(user.getUsername(), user);
    }

    @Override
    public void updateUser(User user) {
        if (!users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Данного пользователя не существует");
        }
        users.put(user.getUsername(), user);
    }

    @Override
    public Optional<User> getuser(String username) {
        return Optional.ofNullable(users.get(username));
    }

}
