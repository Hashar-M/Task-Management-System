package com.hashar.Task_Management_System.advice;

import com.hashar.Task_Management_System.errorResponse.ErrorResponse;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TMSControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, Object> errorDetails = new HashMap<>();
                    errorDetails.put("message", fieldError.getField()+ " " + fieldError.getDefaultMessage());
                    return errorDetails;
                })
                .toList().getFirst();
        ErrorResponse errorResponse = new ErrorResponse("Invalid Input",response.get("message").toString());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Invalid Input", ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Authorization Error", ex.getMessage()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Data conflict " , ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)  // handles hibernate Constraint violation exception not from jakarta.
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Unique constraint violated", ex.getMessage()));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleJwtValidityException( SignatureException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("JWT expired", ex.getMessage()));
    }

    @ExceptionHandler(InvalidKeyException.class)
    public ResponseEntity<Object> HandleInvalidKeyException( InvalidKeyException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Invalid JWT", ex.getMessage()));
    }

}
