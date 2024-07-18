package com.example.department.handler;

//import jakarta.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;


import java.util.HashSet;
import java.util.Set;

import static com.example.department.handler.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exception) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ACCOUNT_LOCKED.getCode())
                                .errorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exception) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ACCOUNT_DISABLED.getCode())
                                .errorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exception) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(BAD_CREDENTIALS.getCode())
                                .errorDescription(BAD_CREDENTIALS.getDescription())
                                .error(BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }

//    @ExceptionHandler(MessagingException.class)
//    public ResponseEntity<ExceptionResponse> handleException(MessagingException exception) {
//        return ResponseEntity
//                .status(INTERNAL_SERVER_ERROR)
//                .body(
//                        ExceptionResponse.builder()
//                                .error(exception.getMessage())
//                                .build()
//                );
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        String errorMessage = "Required request header '" + exception.getHeaderName() + "' for method parameter type " + exception.getParameter().getParameterType().getSimpleName() + " is not present";
        return ResponseEntity
                .badRequest()
                .body(
                        ExceptionResponse.builder()
                                .errorDescription("Missing Request Header")
                                .error(errorMessage)
                                .build()
                );
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoResourceFoundException(NoResourceFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(404)
                                .errorDescription("May be this is a wrong URL?")
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .errorDescription("some hidden error, may be wrong urrl")
                                .error(exception.getMessage())
                                .build()
                );
    }
}
