package com.eclectics.security.insecureapi.utils;

import com.eclectics.security.insecureapi.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String Error, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(Error);
        errorResponse.setMessage(message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    public static ResponseEntity<ErrorResponse> buildSuccessResponse(HttpStatus status, String success, String message) {
        return buildErrorResponse(status, success, message);
    }
}
