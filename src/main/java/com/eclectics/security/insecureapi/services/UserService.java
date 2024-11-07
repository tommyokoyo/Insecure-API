package com.eclectics.security.insecureapi.services;

import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.models.UserDTO;
import com.eclectics.security.insecureapi.utils.ResponseBuilder;
import com.eclectics.security.insecureapi.utils.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        log.info("{}", userStorage.addUser(user));
        return userStorage.addUser(user);
    }

    public JSONObject insecureAddUser(JSONObject user) {
        log.info("{}", userStorage.insecureAddUser(user));
        return userStorage.insecureAddUser(user);
    }

    public List<User> invalidGetUsers() {
        log.info("{}", userStorage.getUsers());
        return userStorage.getUsers();
    }

    public List<UserDTO> getUsers() {
        log.info("{}", userStorage.getUsers().stream().map(UserDTO::covertModel).toList());
        return userStorage.getUsers().stream().map(UserDTO::covertModel).toList();
    }

    public UserDTO getUser(String username) {
        if (userStorage.getUser(username) != null) {
            log.info("{}", UserDTO.covertModel(userStorage.getUser(username)));
            return UserDTO.covertModel(userStorage.getUser(username));
        }
        return null;
    }

    public List<JSONObject> insecureGetUsers() {
        log.info("{}", userStorage.getUsers());
        return userStorage.getAllUsers();
    }
}
