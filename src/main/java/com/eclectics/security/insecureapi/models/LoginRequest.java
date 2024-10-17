package com.eclectics.security.insecureapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(description = "User unique username", example = "admin")
    @JsonProperty("username")
    private String username;
    @Schema(description = "Users' password", example = "admin")
    @JsonProperty("password")
    private String password;
}
