package com.ecommerce.orderservice.service.client.payment;

import com.ecommerce.orderservice.common.exception.GenericException;
import com.ecommerce.orderservice.common.utils.parse.ParseToGenericResponse;
import com.ecommerce.orderservice.common.utils.properties.ProjectProperties;
import com.ecommerce.orderservice.domain.dto.base.GenericResponseDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentRequestDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentResponseDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentStatusRequestDto;
import com.ecommerce.orderservice.domain.dto.exception.ErrorResponseDto;
import com.ecommerce.orderservice.service.client.base.GenericClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentClientImpl implements PaymentClient {

    private final GenericClient client;
    private final ProjectProperties projectProperties;
    private final ParseToGenericResponse parseToGenericResponse;

    @Override
    public PaymentResponseDto updatePaymentStatus(Long orderId, PaymentStatusRequestDto paymentStatusRequestDto) {
        ResponseEntity<String> response = client.patch(projectProperties.
                getPaymentServiceUrl() + "/payments/update/"+ orderId+"/payment-status", paymentStatusRequestDto);
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            log.error("updatePaymentStatusClient, status code: {}", response.getStatusCode());
            throwException(response);
        }
        return parseToGenericResponse.mapperObject(response, PaymentResponseDto.class).getData();
    }

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        ResponseEntity<String> response = client.post(projectProperties.getPaymentServiceUrl() + "/payments/create", paymentRequestDto);
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            log.error("createPaymentClient, status code: {}", response.getStatusCode());
            throwException(response);
        }
        return parseToGenericResponse.mapperObject(response, PaymentResponseDto.class).getData();
    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        ResponseEntity<String> response = client.get(projectProperties.getPaymentServiceUrl() + "/payments/order/" + orderId, null);
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            log.error("getPaymentByOrderIdClient, status code: {}", response.getStatusCode());
            throwException(response);
        }
        return parseToGenericResponse.mapperObject(response, PaymentResponseDto.class).getData();
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
}
