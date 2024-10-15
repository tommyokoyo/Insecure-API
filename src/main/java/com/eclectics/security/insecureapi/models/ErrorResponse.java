package com.eclectics.security.insecureapi.models;

import lombok.Data;

@Data
public class ErrorResponse {
    private String status;
    private String message;
}
