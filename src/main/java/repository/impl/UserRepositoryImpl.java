package repository.impl;

import model.User;
import repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    Map<String, User>  users = new HashMap<>();

    @Override
    public void createUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Такой пользователь уже существует");
        } else {
            users.put(user.getUsername(), user);
        }
    }

    @Override
    public void updateUser(User user) {
        if (users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        } else {
            throw new IllegalArgumentException("Данного пользователя не существует");
        }
    }

    @Override
    public void deleteUser(User user) {
        if (users.containsKey(user.getUsername())) {
            users.remove(user.getUsername());
        }
    }

    @Override
    public User getuser(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        } else {
            throw new IllegalArgumentException("Данного пользователя не существует");
        }
    }
}
