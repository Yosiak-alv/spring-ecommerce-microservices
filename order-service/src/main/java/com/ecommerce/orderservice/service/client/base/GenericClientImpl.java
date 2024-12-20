package com.ecommerce.orderservice.service.client.base;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Generated
@Slf4j
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

    @Override
    public <T> ResponseEntity<String> post(String url, T entity) {
        return call(url, entity, HttpMethod.POST, null);
    }

    @Override
    public <T> ResponseEntity<String> postWithHeaders(String url, T entity, Map<String, List<String>> headerElements) {
        return call(url, entity, HttpMethod.POST, headerElements);
    }

    private ResponseEntity<String> call(String url, Object entity, HttpMethod method, Map<String, List<String>> headerElements){
        HttpHeaders headers = new HttpHeaders();
        if (headerElements != null && !headerElements.isEmpty()) {
            headers.addAll(CollectionUtils.toMultiValueMap(headerElements));
        }
        HttpEntity<Object> httpEntity = new HttpEntity<>(entity, headers);
        try {
            return restTemplate.exchange(url, method, httpEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error calling URL: {}, Status Code: {}, Response Body: {}", url, e.getStatusCode(), e.getResponseBodyAsString());
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }
}
