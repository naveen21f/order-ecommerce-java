package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.AddressDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderItemDto;
import com.order.ecommerce.entity.Address;
import com.order.ecommerce.entity.Order;
import com.order.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderDetailsMapper {

    @Mapping(source = "payment.amount", target = "amount")
    @Mapping(source = "payment.paymentMode", target = "paymentMode")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "payment", ignore = true)
    Order toOrderEntity(OrderDto orderDto);

    @Mapping(source = "product.productId", target = "productId")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @Mapping(target = "orderItemPk", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItemEntity(OrderItemDto orderItemDto);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "order", ignore = true)
    Address toAddressEntity(AddressDto addressDto);

    AddressDto toAddressDto(Address address);
}
