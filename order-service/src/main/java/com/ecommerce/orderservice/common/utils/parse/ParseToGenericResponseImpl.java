package com.ecommerce.orderservice.common.utils.parse;

import com.ecommerce.orderservice.common.exception.GenericException;
import com.ecommerce.orderservice.domain.dto.base.GenericResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ParseToGenericResponseImpl implements ParseToGenericResponse {

    @Override
    public <T> List<T> mapperList(ResponseEntity<String> response, Class<T> generic) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(
                    response.getBody(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, generic)
            );
        } catch (JsonProcessingException e) {
            throw new GenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null,
                    "Invalid to parse JSON",
                    null
            );
        }
    }

    @Override
    public <T> GenericResponseDto<T> mapperObject(ResponseEntity<String> response, Class<T> generic) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(GenericResponseDto.class, generic);
            return objectMapper.readValue(response.getBody(), type);
        } catch (JsonProcessingException jpe) {
            throw new GenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null,
                    "Invalid to parse JSON",
                    null
            );
        }
    }
}
