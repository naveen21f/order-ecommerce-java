package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Product toProductEntity(ProductDto productDto);

    ProductDto toProductDto(Product product);
}
