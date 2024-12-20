package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.dto.base.GenericResponseDto;
import com.ecommerce.productservice.dto.exception.ErrorResponseDto;
import com.ecommerce.productservice.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Product Controller", description = "Fake Store Product API Integration Endpoints")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve product by id", description = "Retrieve product by id from Fake Store API")
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
    public ResponseEntity<GenericResponseDto<ProductResponseDto>> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(GenericResponseDto.<ProductResponseDto>builder()
                .data(productService.getProductById(id))
                .message(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .build());
    }
}
