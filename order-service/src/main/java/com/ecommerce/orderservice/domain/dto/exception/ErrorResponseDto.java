package com.ecommerce.orderservice.domain.dto.exception;

import com.ecommerce.orderservice.common.utils.date.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private Integer statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    @Builder.Default()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime = LocalDateTime.now();
}