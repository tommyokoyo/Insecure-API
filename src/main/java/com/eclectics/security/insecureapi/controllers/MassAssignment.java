package com.eclectics.security.insecureapi.controllers;

import com.eclectics.security.insecureapi.models.User;
import com.eclectics.security.insecureapi.services.UserService;
import com.eclectics.security.insecureapi.utils.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mass-assignment")
@Tag(name="Mass Assignment", description = "Mass assignment is a vulnerability that allows" +
        " users to update model attributes directly from request parameters without properly filtering them")
public class MassAssignment {
    private final UserService userService;

    @Autowired
    public MassAssignment(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "This endpoint is aimed at showcasing the Mass Assignment vulnerability. " +
            "Note: Use the payload from /add-user and add parameters as you wish.", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns Added User data", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/add-user-vuln")
    public ResponseEntity<?> insecureAddUser(@RequestBody Map<String, Object> user) {
        // Check if body is null
        JSONObject requestReceived = new JSONObject(user);
        Map<String, Object> response = (userService.insecureAddUser(requestReceived)).toMap();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(description = "This endpoint depict how DTOs/Model class help in mapping only the fields needed by the api." +
            "DTOs are designed to specify the exact fields you want to allow for updating in each endpoint. " +
            "This ensures that only explicitly permitted fields can be modified.", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns Added User data", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        // Check if body is null
        if (user.getUsername() == null && user.getPassword() == null && user.getEmail() == null && user.getUserid() == null) {
            return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Bad Request", "Body can not be null");
        } else {
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
        }
    }

    @Operation(description = "This endpoints return all invalid users added through the /add-user-vuln route, " +
            "it is aimed at showing the dangers of not using Model classes or Data Transfer Objects (DTOs) " +
            "as it may lead to data Inconsistency in the database.", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns All Users details", content =@Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "UnAuthorized", content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/get-all-invalid-users")
    public ResponseEntity<?> getAllInvalid() {
        List<Map<String, Object>> allUsers = (userService.insecureGetUsers()).stream().map(JSONObject::toMap).collect(Collectors.toList());
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
