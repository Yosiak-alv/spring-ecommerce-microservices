package com.ecommerce.productservice.service.client.base;

import lombok.Generated;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GenericClientImpl implements GenericClient {
    private final RestTemplate restTemplate;

    public GenericClientImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> get(String url, HttpEntity<String> entity){
        return call(url, entity, HttpMethod.GET, null);
    }

    @Override
    public ResponseEntity<String> getWithHeaders(String url, HttpEntity<String> entity, Map<String, List<String>> headerElements){
        return call(url, entity, HttpMethod.GET, headerElements);
    }

    private ResponseEntity<String> call(String url, Object entity, HttpMethod method, Map<String, List<String>> headerElements){
        HttpHeaders headers = new HttpHeaders();
        if (headerElements != null && !headerElements.isEmpty()) {
            headers.addAll(CollectionUtils.toMultiValueMap(headerElements));
        }
        HttpEntity<Object> httpEntity = new HttpEntity<>(entity, headers);
        return restTemplate.exchange(url, method, httpEntity, String.class);
    }
}
