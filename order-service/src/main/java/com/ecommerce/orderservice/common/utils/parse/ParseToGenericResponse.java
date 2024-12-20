package com.ecommerce.orderservice.common.utils.parse;

import com.ecommerce.orderservice.domain.dto.base.GenericResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParseToGenericResponse {
    <T> List<T> mapperList(ResponseEntity<String> response, Class<T> clazz);
    <T> GenericResponseDto<T> mapperObject(ResponseEntity<String> response, Class<T> generic);

}
