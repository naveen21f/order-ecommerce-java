package com.order.ecommerce.service;

import com.order.ecommerce.dto.ProductDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto findProductById(String productId);

    List<ProductDto> findAllById(List<String> ids);

    PageImpl<ProductDto> findAllProducts(Pageable pageable);

    ProductDto updateProduct(ProductDto productDto);
}
