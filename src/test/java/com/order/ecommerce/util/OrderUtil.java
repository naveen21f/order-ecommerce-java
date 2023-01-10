package com.order.ecommerce.util;

import java.math.BigDecimal;
import java.util.List;

import com.order.ecommerce.dto.AddressDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderItemDto;
import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.enums.PaymentMode;
import com.order.ecommerce.enums.ShippingMode;
import lombok.experimental.UtilityClass;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@UtilityClass
public class OrderUtil {

    public static OrderDto createTestOrder() {
        return new OrderDto(
                "1",
                new BigDecimal("6.0"),
                new BigDecimal(10.0),
                new BigDecimal(2.0),
                new BigDecimal(2.0),
                "test",
                ShippingMode.DELIVERY.name(),
                new BigDecimal(10.0),
                PaymentMode.CREDIT.name(),
                createAddress(),
                createAddress(),
                List.of(
                        new OrderItemDto("101", "10"),
                        new OrderItemDto("102", "10")
                ),
                OrderStatus.RECEIVED.name()
        );
    }

    public static OrderDto getExistingTestOrder() {
        return new OrderDto(
                "1",
                new BigDecimal("6.0"),
                new BigDecimal(10.0),
                new BigDecimal(2.0),
                new BigDecimal(2.0),
                "test",
                ShippingMode.DELIVERY.name(),
                new BigDecimal(10.0),
                PaymentMode.CREDIT.name(),
                createAddress(),
                createAddress(),
                List.of(
                        new OrderItemDto("6bcc9094-c7de-446d-85a1-252c58c33b0f", "10")
                ),
                OrderStatus.RECEIVED.name()
        );
    }

    public static OrderDto getNonExistingProduct() {
        return new OrderDto(
                "1",
                new BigDecimal("6.0"),
                new BigDecimal(10.0),
                new BigDecimal(2.0),
                new BigDecimal(2.0),
                "test",
                ShippingMode.DELIVERY.name(),
                new BigDecimal(10.0),
                PaymentMode.CREDIT.name(),
                createAddress(),
                createAddress(),
                List.of(
                        new OrderItemDto("NonExistingProductId", "10")
                ),
                OrderStatus.RECEIVED.name()
        );
    }

    private static AddressDto createAddress() {
        return new AddressDto(
                "3755 M lane",
                "Apt 311",
                "Aurora",
                "IL",
                "60504",
                "test.gmail.com",
                "1234567890"
        );
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
