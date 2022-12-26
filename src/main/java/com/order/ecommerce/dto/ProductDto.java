package com.order.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ProductDto {

    @NotNull
    private final String productId;

    @NotNull
    private final String sku;

    @NotNull
    private final String title;

    @NotNull
    private final String description;

    private final double price;
}
