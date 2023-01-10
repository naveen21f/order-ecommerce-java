package com.order.ecommerce.controller;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    /**
     * Creates a product
     * @param productDto
     * @return
     */
    @PostMapping
    @Operation(summary = "Create a product", description = "Create a product")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        validateArgument(productDto);
        return productService.createProduct(productDto);
    }

    /**
     * Finds product by id
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    @Operation(summary = "Find a product", description = "Find a product by id")
    public ProductDto findProductById(@PathVariable(name = "productId") String productId) {
        validateArgument(productId == null || productId.isEmpty(), "Product Id cannot be null or empty");
        return productService.findProductById(productId);
    }

    /**
     * Update a product details
     * @param productDto
     * @return
     */
    @PutMapping
    @Operation(summary = "Update a product details", description = "Update a specific product details")
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        validateArgument(productDto);
        return productService.updateProduct(productDto);
    }


    /**
     * List all products paginated
     * @param pageable
     * @return
     */
    @GetMapping
    @Operation(summary = "List all products", description = "List all products paginated")
    public PageImpl<ProductDto> findAllProdcts(Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    private void validateArgument(ProductDto productDto) {
        validateArgument(productDto == null, "Product cannot be null");
        validateArgument(productDto.getProductId() == null || productDto.getProductId().isEmpty(), "Product Id cannot be null or empty");
        validateArgument(productDto.getSku() == null || productDto.getSku().isEmpty(), "Product Sku cannot be null or empty");
        validateArgument(productDto.getTitle() == null || productDto.getTitle().isEmpty(), "Product Title cannot be null");
        validateArgument(productDto.getDescription() == null || productDto.getDescription().isEmpty(), "Product Description cannot be empty");
    }

    private void validateArgument(boolean condition, String message) {
        if (condition) {
            log.error("Error while processing request with message = {}", message);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }
}