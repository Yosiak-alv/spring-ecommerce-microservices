package com.ecommerce.paymentservice.service.payment;

import com.ecommerce.paymentservice.domain.dto.payment.PaymentRequestDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentResponseDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentStatusRequestDto;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto updatePaymentStatus(Long orderId, PaymentStatusRequestDto paymentStatusRequestDto);
    PaymentResponseDto getPaymentByOrderId(Long orderId);
}
