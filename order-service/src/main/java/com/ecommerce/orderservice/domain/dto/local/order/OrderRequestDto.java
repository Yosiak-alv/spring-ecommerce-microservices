package com.ecommerce.orderservice.domain.dto.local.order;

import com.ecommerce.orderservice.common.utils.validation.IsEnum;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentMethod;
import com.ecommerce.orderservice.domain.dto.local.customer.CustomerRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    @Valid
    private CustomerRequestDto client;

    @NotNull
    @Size(min = 1)
    @Valid // Ensures nested validation for each product in the list
    private List<ProductRequestDto> products;

    @NotNull
    @IsEnum(enumClass = PaymentMethod.class, message = "Payment method must be one of the following: CREDIT_CARD, DEBIT_CARD, CASH")
    private PaymentMethod paymentMethod;
}
