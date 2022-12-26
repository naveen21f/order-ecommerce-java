package com.order.ecommerce.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.service.OrderService;
import com.order.ecommerce.util.OrderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    private static final OrderDto orderDtoRequest = OrderUtil.createTestOrder();
    private static final OrderResponseDto mockOrderResponse = new OrderResponseDto("2e99fe21-2243-4004-9640-e992bbcc5040", "PROCESSING");

    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;


    @Test
    void testCreateOrder() {
        when(orderService.createOrder(orderDtoRequest)).thenReturn(mockOrderResponse);

        OrderResponseDto actualResponse = orderController.createOrder(orderDtoRequest);

        assertThat(actualResponse).isEqualTo(mockOrderResponse);
    }

    @Test
    void testGetOrder() {
        String orderId = "2e99fe21-2243-4004-9640-e992bbcc5040";
        when(orderService.findOrderById(orderId)).thenReturn(orderDtoRequest);

        OrderDto actualResponse = orderController.findOrderBy(orderId);

        assertThat(actualResponse).isEqualTo(orderDtoRequest);
    }

    @Test
    void testPatchOrder() {
        String orderId = "2e99fe21-2243-4004-9640-e992bbcc5040";

        orderController.updateOrderStatus(orderId, OrderStatus.CANCELLED.name());

        verify(orderService).updateOrderStatus(orderId, OrderStatus.CANCELLED.name());
    }
}
