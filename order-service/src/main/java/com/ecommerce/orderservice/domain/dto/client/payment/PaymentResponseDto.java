package com.ecommerce.orderservice.domain.dto.client.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private String paymentDate;
    private String traceId;
}
