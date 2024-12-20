package com.ecommerce.orderservice.service.local.order;

import com.ecommerce.orderservice.domain.dto.client.payment.PaymentStatusRequestDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderRequestDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderResponseDto;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> getCustomerOrders(Long customerId);
    OrderResponseDto updateOrderPaymentStatus(Long orderId, PaymentStatusRequestDto paymentStatusRequestDto);
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrderById(Long Id);
}
