package com.ecommerce.orderservice.service.client.base;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GenericClient {
    ResponseEntity<String> get(String url, HttpEntity<String> entity);
    <T> ResponseEntity<String> post(String url, T entity);
    <T> ResponseEntity<String> patch(String url, T entity);


}
