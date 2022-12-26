package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {

    @NotNull
    private final String customerId;

    private final double subTotal;

    private final double totalAmt;

    private final double tax;

    private final double shippingCharges;

    @NotNull
    private final String title;

    private final String shippingMode;

    private final double amount;

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
