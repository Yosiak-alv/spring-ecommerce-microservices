package com.ecommerce.orderservice.domain.dto.local.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {
    private Long id;
    private String dui;
    private String name;
    private String email;
}
