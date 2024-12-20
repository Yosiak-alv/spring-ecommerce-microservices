package com.ecommerce.orderservice.service.client.product;

import com.ecommerce.orderservice.common.exception.GenericException;
import com.ecommerce.orderservice.common.utils.parse.ParseToGenericResponse;
import com.ecommerce.orderservice.common.utils.properties.ProjectProperties;
import com.ecommerce.orderservice.domain.dto.base.GenericResponseDto;
import com.ecommerce.orderservice.domain.dto.client.product.ProductResponseDto;
import com.ecommerce.orderservice.domain.dto.exception.ErrorResponseDto;
import com.ecommerce.orderservice.service.client.base.GenericClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductClientImpl implements ProductClient {
    private final GenericClient client;
    private final ProjectProperties projectProperties;
    private final ParseToGenericResponse parseToGenericResponse;

    @Override
    public ProductResponseDto getProductById(Long id) {
        ResponseEntity<String> response = client.get(projectProperties.getProductServiceUrl()+ "/products/" + id, null);
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            log.error("getProductByIdClient, status code: {}", response.getStatusCode());
            throwException(response);
        }
        return parseToGenericResponse.mapperObject(response, ProductResponseDto.class).getData();
    }

    private void throwException(ResponseEntity<String> response){
        var errorResponse = parseToGenericResponse.mapperObject(response, ErrorResponseDto.class);
        throw new GenericException(
                errorResponse.getData().getStatusCode(),
                errorResponse.getData().getField(),
                errorResponse.getData().getMessage(),
                errorResponse.getData().getErrors()
        );
    }

    /*private GenericResponseDto<ErrorResponseDto> testMapper(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new GenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null,
                    "Invalid to parse JSON",
                    null
            );
        }
    }*/
}
