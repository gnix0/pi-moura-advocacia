package com.mouraadvocacia.api.exception;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.mouraadvocacia.api.adapter.inbound.rest.AuthController;

@RestControllerAdvice(assignableTypes = AuthController.class)
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserErrorDetails> userAlreadyExistsException(
            UserAlreadyExistsException exception,
            WebRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new UserErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)));
    }

    @ExceptionHandler(AuthenticationFailureException.class)
    public ResponseEntity<UserErrorDetails> authenticationFailureException(
            AuthenticationFailureException exception,
            WebRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UserErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)));
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<UserErrorDetails> invalidUserDataException(
            InvalidUserDataException exception,
            WebRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(new UserErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserErrorDetails> methodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            WebRequest request
    ) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest()
                .body(new UserErrorDetails(new Date(), message, request.getDescription(false)));
    }

    private String formatFieldError(FieldError fieldError) {
        if (fieldError.getDefaultMessage() == null || fieldError.getDefaultMessage().isBlank()) {
            return "Campo '" + fieldError.getField() + "' invalido.";
        }

        return fieldError.getDefaultMessage();
    }
}
