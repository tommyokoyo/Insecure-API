package com.eclectics.security.insecureapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserLookupRequest {
    @Schema(description = "User unique identifier", example = "admin")
    @JsonProperty("username")
    private String username;
}
