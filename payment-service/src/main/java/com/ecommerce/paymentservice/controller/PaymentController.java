package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.domain.dto.base.GenericResponseDto;
import com.ecommerce.paymentservice.domain.dto.exception.ErrorResponseDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentRequestDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentResponseDto;
import com.ecommerce.paymentservice.domain.dto.payment.PaymentStatusRequestDto;
import com.ecommerce.paymentservice.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment Controller", description = "Payment Endpoints for processing payments")
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Retrieve payment information for an order", description = "Retrieve payment information for an order by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GenericResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    public ResponseEntity<GenericResponseDto<PaymentResponseDto>> findPaymentByOrderId(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(GenericResponseDto.<PaymentResponseDto>builder()
                .data(paymentService.getPaymentByOrderId(orderId))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }

    @PatchMapping("/update/{orderId}/payment-status")
    @Operation(summary = "Update payment status", description = "Update payment status for an order by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GenericResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    public ResponseEntity<GenericResponseDto<PaymentResponseDto>> updatePaymentStatus(@PathVariable("orderId") Long orderId,
                                                                                      @Valid @RequestBody PaymentStatusRequestDto paymentStatusRequestDto) {
        return ResponseEntity.ok(GenericResponseDto.<PaymentResponseDto>builder()
                .data(paymentService.updatePaymentStatus(orderId, paymentStatusRequestDto))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }

    @PostMapping("/create")
    @Operation(summary = "Create a payment", description = "Create a payment record for an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GenericResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    public ResponseEntity<GenericResponseDto<PaymentResponseDto>> createPayment(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.ok(GenericResponseDto.<PaymentResponseDto>builder()
                .data(paymentService.createPayment(paymentRequestDto))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }
}
