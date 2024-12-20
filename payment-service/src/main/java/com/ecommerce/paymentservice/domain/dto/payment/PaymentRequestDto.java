package com.ecommerce.paymentservice.domain.dto.payment;

import com.ecommerce.paymentservice.common.utils.validation.IsEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    @NotNull
    @Positive
    @Schema(description = "ID of the order being paid", example = "12345")
    private Long orderId;

    @NotNull
    @Positive
    @Schema(description = "Payment amount", example = "150.75")
    private BigDecimal amount;

    @NotNull
    @IsEnum(enumClass = PaymentMethod.class, message = "Payment method must be one of the following: CREDIT_CARD, DEBIT_CARD, CASH")
    @Schema(description = "Method of payment (e.g., DEBIT_CARD, CREDIT_CARD, CASH)", example = "CREDIT_CARD")
    private PaymentMethod paymentMethod;

    @NotNull
    @Schema(description = "Unique trace ID for tracking payment requests, recommended UUID", example = "57d91091ae80459980556281ff52c888")
    private String traceId;
}
