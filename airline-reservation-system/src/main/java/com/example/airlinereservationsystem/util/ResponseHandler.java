package com.example.airlinereservationsystem.util;

import com.example.airlinereservationsystem.util.exception.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Abdi Wako Jilo
 */
public class ResponseHandler {
    public static ResponseEntity<Object> respond(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<?> respond(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<?> respond(ErrorDetails errorDetails, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", errorDetails.getMessage());
        map.put("status", status.value());

        return new ResponseEntity<>(map,status);
    }
}