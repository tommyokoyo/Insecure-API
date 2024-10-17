package com.eclectics.security.insecureapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class User {
    @Schema(description = "User unique identifier", example = "k397f3-v34gf3q-qfv43")
    @JsonProperty("userid")
    private String userid;
    @Schema(description = "username of the User", example = "mr_delulu")
    @JsonProperty("username")
    private String username;
    @Schema(description = "email of the User", example = "sample-email@mail.com")
    @JsonProperty("email")
    private String email;
    @Schema(description = "Users' password", example = "******")
    @JsonProperty("password")
    private String password;
}
