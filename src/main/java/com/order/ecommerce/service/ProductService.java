package com.order.ecommerce.service;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import com.order.ecommerce.exception.ProductNotFoundException;
import com.order.ecommerce.mapper.ProductMapper;
import com.order.ecommerce.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating Product with sku= {}, title = {}", productDto.getSku(), productDto.getTitle());
        Product entity = productMapper.toProductEntity(productDto);
        entity.setProductId(UUID.randomUUID().toString());
//        entity.setCreatedAt(LocalDate.now());
        Product savedProduct = productRepository.save(entity);
        log.info("Successfully saved product with id = {} on {}", savedProduct.getProductId(), savedProduct.getCreatedAt());
        return productMapper.toProductDto(savedProduct);
    }

    @Override
    public ProductDto findProductById(String productId) {
        log.info("Finding product for productId = {}", productId);
        Product product = findByProductId(productId);
        log.info("Successfully found product for productId = {}", productId);
        return productMapper.toProductDto(product);
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

    @Override
    public PageImpl<ProductDto> findAllProducts(Pageable pageable) {
        log.info("Find all products for page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDto> productDtos = productPage.getContent()
                .stream()
                .map(productMapper::toProductDto).toList();

        return new PageImpl<>(productDtos, pageable, productPage.getTotalElements());
    }

    private Product findByProductId(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.info("Cannot find product with id = {}", productId);
            throw new ProductNotFoundException(String.format("Cannot find product with id=%s", productId));
        }

        return product.get();
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        log.info("Update product with productId= {}", productDto.getProductId());
        Product product = findByProductId(productDto.getProductId());
        product = productMapper.toProductEntity(productDto);
        product = productRepository.save(product);
        return productMapper.toProductDto(product);
    }


}
