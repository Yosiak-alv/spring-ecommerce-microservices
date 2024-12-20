package com.ecommerce.productservice.service.client.product;

import com.ecommerce.productservice.common.exception.GenericException;
import com.ecommerce.productservice.common.utils.parse.ParseToGenericResponse;
import com.ecommerce.productservice.common.utils.properties.ProjectProperties;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.service.client.base.GenericClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FakeStoreProductClientImpl implements FakeStoreProductClient {

    private final GenericClient client;
    private final ProjectProperties projectProperties;
    private final ParseToGenericResponse parseToGenericResponse;

    @Override
    public ProductResponseDto getProductById(Long id) {
        ResponseEntity<String> response = client.get(projectProperties.getFakeStoreApiUrl() + "/products/" + id, null);
        log.info("FakeStoreProductId Http Status,: {}",response.getStatusCode());
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            log.error("getProductByIdClient, status code: {}", response.getStatusCode());
            throwException(response);
        }
        if (response.getBody() == null) {
            throw new GenericException(
                    HttpStatus.NOT_FOUND.value(),
                    "FakeStoreProductClientImpl",
                    "No data for product id: " + id,
                    null
            );
        }
        return parseToGenericResponse.mapperObject(response, ProductResponseDto.class).getData();
    }

    private void throwException(ResponseEntity<String> response) {
        throw new GenericException(
                response.getStatusCode().value(),
                "FakeStoreProductClientImpl",
                HttpStatus.valueOf(response.getStatusCode().value()).getReasonPhrase(),
                null
        );
    }
}
