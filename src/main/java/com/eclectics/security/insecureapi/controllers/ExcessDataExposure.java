package com.eclectics.security.insecureapi.controllers;

import com.eclectics.security.insecureapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data-expose")
@Tag(name="Excessive Data Exposure", description = "An API that returns more data than necessary, like including a user's password hash")
public class ExcessDataExposure {
    private final UserService userService;

    @Autowired
    public ExcessDataExposure(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User profile details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/get-user-data")
    public ResponseEntity<?> getAllUserData() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @Operation(description = "", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User profile details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/get-user-data-vuln")
    public ResponseEntity<?> invalidUserData() {
        return new ResponseEntity<>(userService.invalidGetUsers(), HttpStatus.OK);
    }
}
