package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductDto;
import com.sparta.msa_exam.product.dto.request.ProductRequest;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @CachePut(cacheNames = "productCache", key = "#result.productId")
    public ProductDto createProduct(ProductRequest productReq) {
        productRepository.findByName(productReq.getName())
                .ifPresent(product -> {
                    throw new IllegalArgumentException("이미 존재하는 상품입니다.");
                });
        Product product = Product.builder()
                .name(productReq.getName())
                .supplyPrice(productReq.getSupplyPrice())
                .build();
        productRepository.save(product);
        return ProductDto.toDto(product);
    }

    @Cacheable(cacheNames = "productCache", key = "'methodName'")
    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDto::toDto)
                .collect(Collectors.toList());
    }
}