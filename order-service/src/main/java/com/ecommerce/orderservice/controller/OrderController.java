package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.domain.dto.base.GenericResponseDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentStatusRequestDto;
import com.ecommerce.orderservice.domain.dto.exception.ErrorResponseDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderRequestDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderResponseDto;
import com.ecommerce.orderservice.service.local.order.OrderService;
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

import java.util.List;

@Tag(name = "Orders Controller", description = "Order Endpoints to manage orders")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "Create a new Order", description = "Create a new Order process")
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
    public ResponseEntity<GenericResponseDto<OrderResponseDto>> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(GenericResponseDto.<OrderResponseDto>builder()
                .data(orderService.createOrder(orderRequestDto))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }

    @PatchMapping("/update/{id}/payment-status")
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
    public ResponseEntity<GenericResponseDto<OrderResponseDto>> updatePaymentStatus(@PathVariable("id") Long id,
                                                                                    @Valid @RequestBody PaymentStatusRequestDto paymentStatusRequestDto) {
        return ResponseEntity.ok(GenericResponseDto.<OrderResponseDto>builder()
                .data(orderService.updateOrderPaymentStatus(id,paymentStatusRequestDto))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve an Order by ID", description = "Retrieve an Order by ID process")
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
    public ResponseEntity<GenericResponseDto<OrderResponseDto>> findOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(GenericResponseDto.<OrderResponseDto>builder()
                .data(orderService.getOrderById(id))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Retrieve Orders by Customer ID", description = "Retrieve Orders by Customer ID process")
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
    public ResponseEntity<GenericResponseDto<List<OrderResponseDto>>> findOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(GenericResponseDto.<List<OrderResponseDto>>builder()
                .data(orderService.getCustomerOrders(customerId))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }


}
