package com.eclectics.security.insecureapi.controllers;

import com.eclectics.security.insecureapi.models.LoginRequest;
import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.services.AuthenticationService;
import com.eclectics.security.insecureapi.utils.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name="Authentication", description = "Challenges related to User Authentication")
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(description = "Returns access_token upon successful User validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Authentication", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad/Invalid request", content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<?> validateCredentials(@RequestBody LoginRequest loginRequest) {
        log.info("Login Request received: {}", loginRequest);
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Password can not be null or empty");
        }
        if (authenticationService.getUser(loginRequest.getUsername()) != null){
            if (loginRequest.getPassword().equals(authenticationService.getUser(loginRequest.getUsername()).getPassword())){
                String tokenGenerated = authenticationService.getToken(loginRequest.getUsername());
                log.info("Login Success: {}", tokenGenerated);
                return ResponseBuilder.buildSuccessResponse(HttpStatus.OK,
                        "Login Successful", tokenGenerated);
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

    @Operation(description = "This endpoint simulates User accounts enumeration, the api returns an error response " +
            "based on the Invalid value. A generic message is recommended.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Authentication", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad/Invalid request", content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/login-enumeration")
    public ResponseEntity<?> invalidValidateCredentials(@RequestBody LoginRequest loginRequest) {
        log.info("Login Request received: {}", loginRequest);
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()){
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Password can not be null or empty");
        }
        if (authenticationService.getUser(loginRequest.getUsername()) != null) {
            if (loginRequest.getPassword().equals(authenticationService.getUser(loginRequest.getUsername()).getPassword())){
                String tokenGenerated = authenticationService.getToken(loginRequest.getUsername());
                log.info("Login Success: {}", tokenGenerated);
                return ResponseBuilder.buildSuccessResponse(HttpStatus.OK,
                        "Login Successful",
                        tokenGenerated);
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
