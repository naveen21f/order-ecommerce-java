package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {

    @NotNull
    private final String customerId;

    private final BigDecimal subTotal;

    private final BigDecimal totalAmt;

    private final BigDecimal tax;

    private final BigDecimal shippingCharges;

    @NotNull
    private final String title;

    private final String shippingMode;

    private final BigDecimal amount;

    @NotNull
    private final String paymentMode;

    @NotNull
    private final AddressDto billingAddress;

    private final AddressDto shippingAddress;

    @NotNull
    private final List<OrderItemDto> orderItems;

    @NotNull
    private final String orderStatus;
}
