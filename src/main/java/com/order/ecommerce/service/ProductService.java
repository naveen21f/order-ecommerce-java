package com.order.ecommerce.service;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import com.order.ecommerce.mapper.ProductMapper;
import com.order.ecommerce.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating Product with productId = {}", productDto.getProductId());
        Product entity = productMapper.toProductEntity(productDto);
        entity.setCreatedAt(LocalDate.now());
        Product savedProduct = productRepository.save(entity);
        log.info("Successfully saved product with id = {} on {}", savedProduct.getProductId(), savedProduct.getCreatedAt());
        return productMapper.toProductDto(savedProduct);
    }

    @Override
    public ProductDto findProductById(String productId) {
        log.info("Finding product for productId = {}", productId);
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.info("Cannot find product with id = {}", productId);
            return null;
        }

        log.info("Successfully found product for productId = {}", productId);
        return productMapper.toProductDto(product.get());
    }

    @Override
    public List<ProductDto> findAllById(List<String> ids) {
        log.info("Finding products for ids = {}", ids);
        List<Product> productList = (List<Product>) productRepository.findAllById(ids);
        if (productList == null || productList.isEmpty()) {
            log.info("No product(s) found for ids = {}", ids);
            return null;
        }
        log.info("Successfully found {} products", productList.size());
        return productList.stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

}
