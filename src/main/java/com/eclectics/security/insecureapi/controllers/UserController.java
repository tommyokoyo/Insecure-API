package com.eclectics.security.insecureapi.controllers;

import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.models.UserLookupRequest;
import com.eclectics.security.insecureapi.services.UserService;
import com.eclectics.security.insecureapi.utils.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="User Controller", description = "Operations related to User")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Returns User details", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User profile details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/get-user")
    public ResponseEntity<?> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (userService.getUser(username) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @Operation(description = "This endpoint depicts Insecure Object Reference, this is due to the application " +
            "taking the userID value from the user and using it to fetch user details without verifying that the requested details match " +
            "the owner of the session. \n A much recommendable approach is to use a signed token to save the session details and " +
            "use the session details to fetch corresponding data.", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Authentication", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/get-user-insecure")
    public ResponseEntity<?> insecureGetUser(@RequestBody UserLookupRequest UserLookupRequest) {
        if (UserLookupRequest.getUsername() == null || UserLookupRequest.getUsername().isEmpty()) {
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty");
        }
        if (userService.getUser(UserLookupRequest.getUsername()) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.getUser(UserLookupRequest.getUsername()), HttpStatus.OK);
    }

    @Operation(description = "Returns All users", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User profile details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @Operation(description = "Simulates the GET all users endpoint except that this endpoint does not require bearer token validation hence " +
            "anyone is able to query for all users, a better/recommended approach is ensuring all endpoint of a public facing API, except authentication endpoint, require " +
            "authorization before accessing any resource or querying data", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User profile details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/all-users-unauthorized")
    public ResponseEntity<?> getAllUsersUnauthorized() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
}
