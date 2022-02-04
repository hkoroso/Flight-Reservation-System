package com.example.airlinereservationsystem.util.exception;

import com.example.airlinereservationsystem.util.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
/**
 * @author Abdi Wako Jilo
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // when resource is not found by id or name

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(true));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // For bad requests like unpermitted parameters (/flight?name=xyz)

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> globalExceptionHandling(BadRequestException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(true));
        return ResponseHandler.respond(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // For all other exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(true));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}