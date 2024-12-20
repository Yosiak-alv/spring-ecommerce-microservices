package com.ecommerce.orderservice.service.client.payment;

import com.ecommerce.orderservice.domain.dto.client.payment.PaymentRequestDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentResponseDto;

public interface PaymentClient {
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto getPaymentByOrderId(Long orderId);
}
