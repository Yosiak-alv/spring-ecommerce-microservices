package com.ecommerce.productservice.service.client.base;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GenericClient {
    ResponseEntity<String> get(String url, HttpEntity<String> entity);
    ResponseEntity<String> getWithHeaders(String url, HttpEntity<String> entity, Map<String, List<String>> headerElements);
}
