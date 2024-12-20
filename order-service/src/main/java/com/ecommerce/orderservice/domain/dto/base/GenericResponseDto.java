package com.ecommerce.orderservice.domain.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponseDto<T> {
    private T data;
    private String message;
    private Integer status;

    public GenericResponseDto(T data) {
        this.data = data;
    }
}