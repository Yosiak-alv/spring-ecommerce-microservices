package com.ecommerce.orderservice.domain.dto.local.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDto {

    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{8}-\\d{1}", message = "DUI must have the following format: 12345678-9")
    private String dui;
}
