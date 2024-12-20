package com.ecommerce.productservice.service.product;

import com.ecommerce.productservice.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto getProductById(Long id);
}
