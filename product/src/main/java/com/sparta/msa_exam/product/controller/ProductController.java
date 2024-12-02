package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductDto;
import com.sparta.msa_exam.product.dto.request.ProductRequest;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Value("${server.port}")
    private String serverPort;

    private <T> ResponseEntity<T> responseEntity(T body, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);
        return new ResponseEntity<>(body, headers, status);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductRequest productReq) {
        return responseEntity(productService.createProduct(productReq), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return responseEntity(productService.getProducts(), HttpStatus.OK);
    }
}