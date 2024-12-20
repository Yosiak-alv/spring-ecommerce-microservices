package com.ecommerce.productservice.dto.base;

import lombok.*;

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