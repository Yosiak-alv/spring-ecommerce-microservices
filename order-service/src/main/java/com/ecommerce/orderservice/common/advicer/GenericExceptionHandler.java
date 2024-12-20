package com.ecommerce.orderservice.common.advicer;

import com.ecommerce.orderservice.common.exception.GenericException;
import com.ecommerce.orderservice.domain.dto.base.GenericResponseDto;
import com.ecommerce.orderservice.domain.dto.exception.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@ControllerAdvice
@Component
@Slf4j
public class GenericExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> handleCustomException(GenericException ex) {
        return handlerResponseException(ex, ErrorResponseDto.builder()
                .statusCode(ex.getErrorDto().getStatusCode())
                .message(ex.getErrorDto().getMessage())
                .field(ex.getErrorDto().getField())
                .errors(ex.getErrorDto().getErrors())
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(error -> {
            errors.add(error.getPropertyPath() + ": " + error.getMessage());
        });

        ErrorResponseDto response = ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Invalid Request")
                .errors(errors)
                .build();

        return handlerResponseException(ex, response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });

        ErrorResponseDto response = ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Invalid Request")
                .errors(errors)
                .build();

        return handlerResponseException(ex, response);
    }

    // Temporary fix for handling Enum validation error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        String errorDetail = extractEnumErrorMessage(ex);

        ErrorResponseDto response = ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Invalid Request")
                .errors(Collections.singletonList(errorDetail))
                .build();

        return handlerResponseException(ex, response);
    }

    private String extractEnumErrorMessage(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("not one of the values accepted for Enum")) {
            // Extract the invalid value and accepted values from the exception message
            String[] parts = message.split(": ");
            String invalidValue = parts.length > 1 ? parts[1].split("\"")[1] : "Unknown";
            String acceptedValues = message.substring(message.indexOf("[") + 1, message.indexOf("]"));
            return String.format("Invalid value '%s'. Accepted values are: %s", invalidValue, acceptedValues);
        }
        return "Invalid input: Unable to parse request body.";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlException(Exception ex, WebRequest request)  {
        return handlerResponseException(ex, ErrorResponseDto.builder()
                .field("Exception")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build());
    }

    private ResponseEntity<Object> handlerResponseException(Exception ex, ErrorResponseDto errorResponseDto) {
        log.error("Stacktrace: {}", ex.getMessage());
        var response = GenericResponseDto.<ErrorResponseDto>builder()
                .data(errorResponseDto)
                .message(HttpStatus.valueOf(errorResponseDto.getStatusCode()).toString())
                .status(errorResponseDto.getStatusCode())
                .build();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
}
