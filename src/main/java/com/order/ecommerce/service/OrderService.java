package com.order.ecommerce.service;

import com.order.ecommerce.dto.*;
import com.order.ecommerce.entity.*;
import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.enums.PaymentStatus;
import com.order.ecommerce.exception.InvalidOrderStatusException;
import com.order.ecommerce.exception.OrderNotFoundException;
import com.order.ecommerce.exception.ProductNotFoundException;
import com.order.ecommerce.mapper.OrderDetailsMapper;
import com.order.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IOrderItemRepository orderItemRepository;
    private final IPaymentRepository paymentRepository;
    private final IAddressRepository addressRepository;

    private final IProductService productService;
    private final OrderDetailsMapper orderDetailsMapper = Mappers.getMapper(OrderDetailsMapper.class);

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderDto orderDto) {
        log.info("Creating Order for customer = {}", orderDto.getCustomerId());

        log.info("Verifying all products exists before generating order");
        List<String> productIds = orderDto.getOrderItems().stream().map(OrderItemDto::getProductId).distinct().collect(Collectors.toList());
        List<ProductDto> products = productService.findAllById(productIds);
        if (products == null || products.isEmpty() || products.size() != productIds.size()) {
            log.info("Not all product(s) exist, failed to create order!");
            throw new ProductNotFoundException("Not all product(s) exist, failed to create order!");
        }

        Order order = generateOrder(orderDto);
        log.info("Generated order for orderId = {}", order.getOrderId());

        Order savedOrder = orderRepository.save(order);
        String savedOrderId = savedOrder.getOrderId();
        List<OrderItem> orderItemList = buildOrderItems(orderDto.getOrderItems(), savedOrderId);
        orderItemRepository.saveAll(orderItemList);

        log.info("Successfully saved order & order items with id = {} for customer = {} on {}", savedOrder.getOrderId(),  savedOrder.getCustomerId(), savedOrder.getCreatedAt());

        return OrderResponseDto.builder()
                .orderId(savedOrderId)
                .orderStatus(savedOrder.getOrderStatus())
                .build();
    }

    @Override
    public OrderDto findOrderById(String orderId) {
        log.info("Finding order for orderId = {}", orderId);
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            log.info("Cannot find order with id = {}", orderId);
            throw new OrderNotFoundException(String.format("Cannot find order with id = %s", orderId));
        }

        log.info("Successfully found order for orderId = {}", orderId);
        return orderDetailsMapper.toOrderDto(order.get());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            log.info("Cannot update status for orderId = {}", orderId);
            return;
        }
        Order order = optionalOrder.get();


        if (!OrderStatus.CANCELLED.isValidChangeStatus(OrderStatus.findOrderStatusFor(order.getOrderStatus()), OrderStatus.findOrderStatusFor(status))) {
            log.error("invalid order status update since order already {}", order.getOrderStatus());
            throw new InvalidOrderStatusException(String.format("invalid order status update since order already %s", order.getOrderStatus()));
        }

        List<OrderStatus> orderStatusList = Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.toString().equalsIgnoreCase(status)).toList();
        if (orderStatusList.isEmpty()) {
            log.error("Invalid status = {}, failed to update order status for id = {}", status, orderId);
            return;
        }

        order.setOrderStatus(status.toUpperCase());
        orderRepository.save(order);
        log.info("Successfully updated order status to = {} for order id = {}", status.toUpperCase(), orderId);
    }

    @Override
    public PageImpl<OrderResponseDto> findOrdersByCustomer(String customerId, Pageable pageable) {

        Page<Order> orderPage = orderRepository.findByCustomerId(customerId, pageable);
        List<OrderResponseDto> orderResponseDtos = orderPage.getContent().stream().map(order -> {
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .orderId(order.getOrderId())
                    .orderStatus(order.getOrderStatus())
                    .build();
            return orderResponseDto;
        }).toList();
        PageImpl<OrderResponseDto> orderResponseDtoPage = new PageImpl<>(orderResponseDtos, pageable, orderPage.getTotalElements());

        return orderResponseDtoPage;
    }

    @Override
    public OrderTimelineDTO orderTimeline(String orderId) {

        log.info("Find revisions for an order id: {}", orderId);
        Revisions<Integer, Order> orderRevisions = orderRepository.findRevisions(orderId);

        OrderTimelineDTO orderTimelineDTO = new OrderTimelineDTO(orderId);
        List<Timeline> timelineList = orderRevisions.getContent().stream().toList()
                .stream()
                .map(r -> new Timeline(r.getEntity().getOrderStatus(), r.getEntity().getCreatedAt(), r.getRequiredRevisionNumber()))
                .toList();
        orderTimelineDTO.setTimelineList(timelineList);
        return orderTimelineDTO;
    }

    private Order generateOrder(OrderDto orderDto) {
        Order order = orderDetailsMapper.toOrderEntity(orderDto);
        order.setOrderId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());

        order.setOrderStatus(OrderStatus.PROCESSING.name());

        Payment payment = buildAndSavePayment(orderDto.getAmount(), orderDto.getPaymentMode());
        order.setPayment(payment);

        Address billingAddress = buildAndLoadAddress(orderDto.getBillingAddress());
        Address shippingAddress = buildAndLoadAddress(orderDto.getShippingAddress());
        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        return order;
    }

    private List<OrderItem> buildOrderItems(List<OrderItemDto> orderItemsDtoList, String orderId) {
        List<OrderItem> orderItemList = orderItemsDtoList
                .stream()
                .map(orderItemDto -> new OrderItem(new OrderItemPk(orderItemDto.getProductId(), orderId), null, null, orderItemDto.getQuantity()))
                .collect(Collectors.toList());
        log.info("Saving order item list for order id = {}", orderId);
        return (List<OrderItem>) orderItemRepository.saveAll(orderItemList);
    }

    private Payment buildAndSavePayment(BigDecimal amount, String paymentMode) {
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                amount,
                paymentMode,
                UUID.randomUUID().toString(),
                PaymentStatus.PROCESSING.name(),
                LocalDate.now(),
                null
        );
        log.info("Saving payment details for payment id = {}", payment.getPaymentId());
        return paymentRepository.save(payment);
    }

    private Address buildAndLoadAddress(AddressDto addressDto) {
        Address addressEntity = orderDetailsMapper.toAddressEntity(addressDto);
        addressEntity.setAddressId(UUID.randomUUID().toString());
        addressEntity.setCreatedAt(LocalDate.now());
        log.info("Saving billing/shipping address for address id = {}", addressEntity.getAddressId());
        return addressRepository.save(addressEntity);
    }
}
