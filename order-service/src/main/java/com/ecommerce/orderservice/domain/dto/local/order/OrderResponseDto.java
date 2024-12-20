package com.ecommerce.orderservice.domain.dto.local.order;

import com.ecommerce.orderservice.domain.dto.client.payment.PaymentResponseDto;
import com.ecommerce.orderservice.domain.dto.local.customer.CustomerResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDto {
    private Long id;
    private CustomerResponseDto client;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentResponseDto payment;
    private List<OrderDetailResponseDto> orderDetails;
    private String orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
}
