package com.ecommerce.orderservice.service.order;

import com.ecommerce.orderservice.domain.dto.local.order.OrderRequestDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrderById(Long Id);
}
