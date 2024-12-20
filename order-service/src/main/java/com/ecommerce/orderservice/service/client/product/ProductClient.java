package com.ecommerce.orderservice.service.client.product;

import com.ecommerce.orderservice.domain.dto.client.product.ProductResponseDto;

public interface ProductClient {
    ProductResponseDto getProductById(Long id);
}
