package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {

    @NotNull
    private final String address1;
    @NotNull
    private final String address2;
    @NotNull
    private final String city;
    @NotNull
    private final String state;
    @NotNull
    private final String zip;
    @NotNull
    private final String email;
    @NotNull
    private final String phone;
}
