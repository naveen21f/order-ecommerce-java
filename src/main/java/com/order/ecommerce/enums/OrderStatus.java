package com.order.ecommerce.enums;

import com.order.ecommerce.exception.InvalidOrderStatusException;

public enum OrderStatus {

    RECEIVED(0),

    PROCESSING(1),

    SHIPPED(2),

    COMPLETED(4),

    CANCELLED(5),

    REFUNDED(6);

    int order;

    OrderStatus(int order) {
        this.order = order;
    }

    // TODO added order for OrderStatus
    public static boolean isValidChangeStatus(OrderStatus oldOderStatus, OrderStatus newOrderStatus) {
        return oldOderStatus.order <= newOrderStatus.order;
    }

    public static OrderStatus findOrderStatusFor(String orderStatus) {

        try {
            return OrderStatus.valueOf(orderStatus);
        } catch (Exception ex) {
            throw new InvalidOrderStatusException(String.format("Invalid order status= %s", orderStatus));
        }
    }
}
