package com.higherx.homework.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ErrorResponse {
    public static ResponseEntity<HashMap<String, String>> response(String code, String message, HttpStatus status) {
        return ResponseEntity
            .status(status)
            .body(new HashMap<>(){{
                put("message", message);
                put("CODE",code);
            }});
    }

    public static HashMap<String, String> responseBody(String code, String message, HttpStatus status) {
        return new HashMap<>(){{
            put("message", message);
            put("CODE",code);
        }};
    }
}
