package com.eclectics.security.insecureapi.controllers;

import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.services.UserService;
import com.eclectics.security.insecureapi.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (userService.getUser(username) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @GetMapping("/get-user-insecure")
    public ResponseEntity<?> insecureGetUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty");
        }

        if (userService.getUser(user.getUsername()) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.getUser(user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/all-users-unauthorized")
    public ResponseEntity<?> getAllUsersUnauthorized() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }


}
