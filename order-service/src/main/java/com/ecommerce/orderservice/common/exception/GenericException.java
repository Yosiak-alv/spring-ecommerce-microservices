package com.ecommerce.orderservice.common.exception;

import com.ecommerce.orderservice.domain.dto.exception.ErrorResponseDto;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@ToString
public class GenericException extends RuntimeException {

    private final ErrorResponseDto errorDto;

    public GenericException() {
        super("Error no controlled");
        this.errorDto = ErrorResponseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Error no controlled")
                .build();
    }

    public GenericException(Integer statusCode,
                            String field, String message, List<String> errors){
        super(message);
        this.errorDto = ErrorResponseDto.builder()
                .statusCode(statusCode)
                .field(field)
                .message(message)
                .errors(errors)
                .build();
    }
}
