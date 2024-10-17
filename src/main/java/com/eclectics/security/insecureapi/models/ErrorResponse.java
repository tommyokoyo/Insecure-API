package com.eclectics.security.insecureapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponse {
    @Schema(description = "API request status", example = "Success/Invalid")
    @JsonProperty("status")
    private String status;
    @Schema(description = "API message", example = "Could not process request")
    @JsonProperty("message")
    private String message;
}
