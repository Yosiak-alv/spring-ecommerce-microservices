package com.ecommerce.productservice.service.client;

import com.ecommerce.productservice.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "fake-store-api-product", url = "{integration.fake-store-api.url}")
public interface ProductFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}", produces = "application/json")
    ProductResponseDto getProductById(Long id);
}
