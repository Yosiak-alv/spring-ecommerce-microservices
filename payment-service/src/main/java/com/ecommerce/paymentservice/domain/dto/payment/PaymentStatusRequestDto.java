package com.ecommerce.paymentservice.domain.dto.payment;

import com.ecommerce.paymentservice.common.utils.validation.IsEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatusRequestDto {
    @NotNull
    @IsEnum(enumClass = PaymentStatus.class, message = "Payment status must be one of the following: PAID, PENDING, APPROVED, DECLINED")
    private PaymentStatus status;
}
