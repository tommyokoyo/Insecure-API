package com.eclectics.security.insecureapi.services;

import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.security.JWTUtil;
import com.eclectics.security.insecureapi.utils.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationService {
    private final JWTUtil jwtUtil;
    private final UserStorage userStorage;

    @Autowired
    public AuthenticationService(JWTUtil jwtUtil, UserStorage userStorage) {
        this.jwtUtil = jwtUtil;
        this.userStorage = userStorage;
    }

    public String getToken(String UserID) {
        return jwtUtil.generateToken(UserID);
    }

    public User getUser(String username) {
        if (userStorage.getUser(username) != null) {
            return userStorage.getUser(username);
        }
        return null;
    }
}
