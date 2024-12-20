package com.ecommerce.productservice.service.product;

import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.service.client.product.FakeStoreProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FakeStoreProductClient fakeStoreProductClient;
    @Override
    public ProductResponseDto getProductById(Long id) {
        return fakeStoreProductClient.getProductById(id);
    }
}
