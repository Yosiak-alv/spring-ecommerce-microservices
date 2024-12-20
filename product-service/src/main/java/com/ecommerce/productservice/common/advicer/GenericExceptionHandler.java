package com.ecommerce.productservice.common.advicer;

import com.ecommerce.productservice.common.exception.GenericException;
import com.ecommerce.productservice.dto.base.GenericResponseDto;
import com.ecommerce.productservice.dto.exception.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Component
@Slf4j
public class GenericExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> handleCustomException(GenericException ex, WebRequest request) {
        return handlerResponseException(ex,  ErrorResponseDto.builder()
                .statusCode(ex.getErrorDto().getStatusCode())
                .message(ex.getErrorDto().getMessage())
                .field(ex.getErrorDto().getField())
                .errors(ex.getErrorDto().getErrors())
                .build());
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
