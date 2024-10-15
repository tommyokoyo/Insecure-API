package com.eclectics.security.insecureapi.services;

import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.utils.ResponseBuilder;
import com.eclectics.security.insecureapi.utils.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUser(String username) {
        if (userStorage.getUser(username) != null) {
            return userStorage.getUser(username);
        }
        return null;
    }
}
