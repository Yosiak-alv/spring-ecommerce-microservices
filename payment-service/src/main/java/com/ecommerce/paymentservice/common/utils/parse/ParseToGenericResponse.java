package com.ecommerce.paymentservice.common.utils.parse;

import com.ecommerce.paymentservice.domain.dto.base.GenericResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParseToGenericResponse {
    <T> GenericResponseDto<T> mapperGenericObject(ResponseEntity<String> response, Class<T> clazz);
    <T> List<T> mapperList(ResponseEntity<String> response, Class<T> clazz);
    <T> GenericResponseDto<T> mapperGenericList(ResponseEntity<String> response, Class<T> clazz);
}
