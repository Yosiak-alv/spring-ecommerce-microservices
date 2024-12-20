package com.ecommerce.paymentservice.service.payment;

import com.ecommerce.paymentservice.common.exception.GenericException;
import com.ecommerce.paymentservice.common.utils.date.DateUtils;
import com.ecommerce.paymentservice.domain.dao.TransactionalDetailsDao;
import com.ecommerce.paymentservice.domain.model.TransactionalDetails;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentMethod;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentRequestDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentResponseDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService{

    private final TransactionalDetailsDao transactionalDetailsDao;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {

        TransactionalDetails transactionalDetails = TransactionalDetails.builder()
                .orderId(paymentRequestDto.getOrderId())
                .traceId(paymentRequestDto.getTraceId())
                .referenceNumber(UUID.randomUUID().toString())
                .amount(paymentRequestDto.getAmount())
                .paymentMethod(paymentRequestDto.getPaymentMethod().name())
                .paymentDate(DateUtils.getDateTimeNow())
                .paymentStatus(paymentRequestDto.getPaymentMethod() == PaymentMethod.CASH ? PaymentStatus.PAID.name() : PaymentStatus.PENDING.name())
                .build();

        TransactionalDetails savedTransaction = transactionalDetailsDao.save(transactionalDetails);
        log.info("create Payment for order: {}, paymentTraceId: {}", paymentRequestDto.getOrderId(), paymentRequestDto.getTraceId());

        return PaymentResponseDto.builder()
                .id(savedTransaction.getId())
                .orderId(savedTransaction.getOrderId())
                .amount(savedTransaction.getAmount())
                .paymentMethod(PaymentMethod.valueOf(savedTransaction.getPaymentMethod()))
                .status(PaymentStatus.valueOf(savedTransaction.getPaymentStatus()))
                .paymentDate(savedTransaction.getPaymentDate())
                .traceId(savedTransaction.getTraceId())
                .build();

    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        log.info("getPaymentByOrderId for order: {}", orderId);
        return transactionalDetailsDao.findByOrderId(orderId)
                .map(transactionalDetails -> PaymentResponseDto.builder()
                        .id(transactionalDetails.getId())
                        .orderId(transactionalDetails.getOrderId())
                        .amount(transactionalDetails.getAmount())
                        .paymentMethod(PaymentMethod.valueOf(transactionalDetails.getPaymentMethod()))
                        .paymentDate(transactionalDetails.getPaymentDate())
                        .traceId(transactionalDetails.getTraceId())
                        .build())
                .orElseThrow( () -> new GenericException(HttpStatus.NOT_FOUND.value(), "getPaymentByOrderId", "No payment transaction data found", null));
    }
}
