package com.order.ecommerce.service;

import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderTimelineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    OrderResponseDto createOrder(OrderDto orderDto);

    OrderDto findOrderById(String id);

    void updateOrderStatus(String id, String status);

    PageImpl<OrderResponseDto> findOrdersByCustomer(String customerId, Pageable pageable);

    OrderTimelineDTO orderTimeline(String orderId);
}
