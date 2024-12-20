package com.ecommerce.paymentservice.domain.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private Long id;
    private Long orderId;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String traceId;
    private BigDecimal amount;
    private String paymentDate;
}
