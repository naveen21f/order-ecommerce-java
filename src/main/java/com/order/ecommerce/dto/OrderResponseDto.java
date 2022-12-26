package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class OrderResponseDto {

    @NotNull
    private final String orderId;

    @NotNull
    private final String orderStatus;

}
