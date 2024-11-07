package com.eclectics.security.insecureapi.controllers;

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
@RequestMapping("/api/IDOR/")
@Tag(name="Insecure Direct Object References (IDOR)", description = "These occur when one exposes database IDs or internal references " +
        "in URLs allows attackers to manipulate them and access other resources.")
public class IDOR {
    private final UserService userService;

    public IDOR(UserService userService) {
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

    @Operation(description = "This endpoint depicts IDOR, this is due to the application " +
            "taking the userID value from the user and using it to fetch user details without verifying that the requested details match " +
            "the owner of the session. \n A much recommendable approach is to use a signed token to save the session details and " +
            "use the session details to fetch corresponding data.", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Authentication", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/get-user-vuln")
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
}

