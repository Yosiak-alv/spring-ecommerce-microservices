package com.ecommerce.orderservice.service.client.payment;

import com.ecommerce.orderservice.domain.dto.client.payment.PaymentRequestDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentResponseDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentStatusRequestDto;


public interface PaymentClient {
    PaymentResponseDto updatePaymentStatus(Long orderId, PaymentStatusRequestDto paymentStatusRequestDto);
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto getPaymentByOrderId(Long orderId);
}
