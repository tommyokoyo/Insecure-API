package com.eclectics.security.insecureapi.controllers;

import com.eclectics.security.insecureapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name="Broken Object Level Authorization (BOLA)", description = "Occurs when APIs don't enforce strict checks on resource access, " +
        "leading unauthorized users to access data they shouldn't.")
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Returns All users", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User profile details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @Operation(description = "Simulates the GET all users endpoint except that this endpoint does not require bearer token validation," +
            " hence anyone is able to query for all users, a better/recommended approach is ensuring all endpoint of a public facing API, " +
            "except authentication endpoint, require authorization before accessing any resource or querying data", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User profile details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/get-all-users-vuln")
    public ResponseEntity<?> getAllUsersUnauthorized() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
}
