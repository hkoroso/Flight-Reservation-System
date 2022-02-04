package com.example.airlinereservationsystem.util.exception;
/**
 * @author Abdi Wako Jilo
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}
