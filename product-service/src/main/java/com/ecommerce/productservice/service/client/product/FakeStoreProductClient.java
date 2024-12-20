package com.ecommerce.productservice.service.client.product;

import com.ecommerce.productservice.dto.ProductResponseDto;


public interface FakeStoreProductClient {
    ProductResponseDto getProductById(Long id);
}
