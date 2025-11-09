package service;

import model.User;
import repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }
        String normalized = username.trim();
        return userRepository.getuser(normalized)
                .filter(user -> user.getPassword().equals(password));
    }

    public void register(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
        userRepository.createUser(new User(username.trim(), password));
    }

    public void changePassword(String username, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
        userRepository.getuser(username)
                .ifPresentOrElse(user -> {
                    user.setPassword(newPassword);
                    userRepository.updateUser(user);
                }, () -> {
                    throw new IllegalArgumentException("Пользователь не найден");
                });
    }
}
