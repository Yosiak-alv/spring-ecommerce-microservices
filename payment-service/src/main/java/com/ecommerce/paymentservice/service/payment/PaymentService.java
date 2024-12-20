package com.ecommerce.paymentservice.service.payment;

import com.ecommerce.paymentservice.domain.dto.payment.PaymentRequestDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto getPaymentByOrderId(Long orderId);
}
