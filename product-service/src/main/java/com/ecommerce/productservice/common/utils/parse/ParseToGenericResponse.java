package com.ecommerce.productservice.common.utils.parse;

import com.ecommerce.productservice.dto.base.GenericResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParseToGenericResponse {
    <T> GenericResponseDto<T> mapperObject(ResponseEntity<String> response, Class<T> clazz);
    <T> List<T> mapperList(ResponseEntity<String> response, Class<T> clazz);
}
