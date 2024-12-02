package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.client.ProductResponseDto;
import com.sparta.msa_exam.order.dto.request.OrderRequest;
import com.sparta.msa_exam.order.dto.response.OrderResponse;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    @CachePut(cacheNames = "orderCache", key = "#result")
    public Long createOrder(OrderRequest orderReq) {
        Order order = Order.builder()
                .name(orderReq.getName())
                .build();
        List<OrderProduct> orderProducts = vaildProduct(order, orderReq.getProductIds());
        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
        return order.getOrderId();
    }


    @Transactional
    @CachePut(cacheNames = "orderCache", key = "#orderId", unless = "#result == null")
    public void updateOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        List<OrderProduct> orderProducts = vaildProduct(order, List.of(productId));
        order.setOrderProducts(orderProducts);

        orderRepository.save(order);
    }

    @Cacheable(cacheNames = "orderCache", key = "args[0]")
    public OrderResponse getOrders(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .name(order.getName())
                .products(order.getProductIds().stream().map(OrderProduct::getProductId).toList())
                .build();
    }

    private List<OrderProduct> vaildProduct(Order order, List<Long> productIds) {
        List<ProductResponseDto> products = productClient.getProducts();
        List<Long> validProductIds = products.stream().map(ProductResponseDto::getProductId).toList();
        for (Long productId : productIds) {
            if(!validProductIds.contains(productId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
        }
        return productIds.stream().map(productId -> OrderProduct.createOrderProduct(order, productId)).toList();
    }
}