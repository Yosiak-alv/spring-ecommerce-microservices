package com.ecommerce.paymentservice.common.utils.parse;

import com.ecommerce.paymentservice.common.exception.GenericException;
import com.ecommerce.paymentservice.domain.dto.base.GenericResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParseToGenericResponseImpl implements ParseToGenericResponse {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(ParseToGenericResponseImpl.class);

    @Override
    public <T> GenericResponseDto<T> mapperGenericObject(ResponseEntity<String> response, Class<T> generic) {
        var mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        try {
            var res = mapper.readValue(response.getBody(), generic);
            return new GenericResponseDto<>(res);
        } catch (JsonProcessingException e) {
            log.error("Stacktrace {}", ExceptionUtils.getStackTrace(e));
            throw new GenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "mapperGenericObject",
                    "Invalid to parse JSON",
                    null
            );
        }
    }

    @Override
    public <T> List<T> mapperList(ResponseEntity<String> response, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(
                    response.getBody(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (JsonProcessingException e) {
            log.error("Stacktrace {}", ExceptionUtils.getStackTrace(e));
            throw new GenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "mapperList",
                    "Invalid to parse JSON",
                    null
            );
        }
    }

    @Override
    public <T> GenericResponseDto<T> mapperGenericList(ResponseEntity<String> response, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            objectMapper.registerModule(new JavaTimeModule());
            // Construct the type GenericResponseDto<List<T>>
            JavaType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            JavaType genericResponseType = objectMapper.getTypeFactory()
                    .constructParametricType(GenericResponseDto.class, listType);

            // Deserialize into GenericResponseDto<List<T>>
            return objectMapper.readValue(response.getBody(), genericResponseType);
        } catch (JsonProcessingException e) {
            log.error("Stacktrace {}", ExceptionUtils.getStackTrace(e));
            throw new GenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "mapperGenericList",
                    "Invalid to parse JSON",
                    null
            );
        }
    }
}
