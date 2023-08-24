package com.loan.exception;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> argumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ApiError errorMessage = new ApiError();
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> responseEntityExceptionHandler(MethodArgumentNotValidException exception){
        Map<String,String> errorMap =new HashMap<>();
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(error-> errorMap.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getConstraintViolations().stream().map(msg1 -> msg1.getMessageTemplate()).findFirst().get());
        return new ResponseEntity<>(
                apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ApiError> userNotFound(final UsernameNotFoundException exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}
