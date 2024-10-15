package com.eclectics.security.insecureapi.controllers;

import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.services.AuthenticationService;
import com.eclectics.security.insecureapi.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> validateCredentials(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Password can not be null or empty");
        }
        if (authenticationService.getUser(user.getUsername()) != null){
            if (user.getPassword().equals(authenticationService.getUser(user.getUsername()).getPassword())){
                return ResponseBuilder.buildSuccessResponse(HttpStatus.OK,
                        "Login Successful",
                        authenticationService.getToken(user.getUsername()));
            } else {
                return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                        "Invalid Parameters",
                        "User or Password is Incorrect");
            }

        } else {
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Invalid Parameters",
                    "User or Password is Incorrect");
        }
    }

    @PostMapping("/login-enumeration")
    public ResponseEntity<?> invalidValidateCredentials(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Password can not be null or empty");
        }
        if (authenticationService.getUser(user.getUsername()) != null){
            if (user.getPassword().equals(authenticationService.getUser(user.getUsername()).getPassword())){
                return ResponseBuilder.buildSuccessResponse(HttpStatus.OK,
                        "Login Successful",
                        authenticationService.getToken(user.getUsername()));
            } else {
                return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                        "Bad Request",
                        "Password is Incorrect");
            }
        } else {
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username is Incorrect");
        }
    }
}
