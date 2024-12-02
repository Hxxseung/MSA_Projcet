package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "order_product")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public static OrderProduct  createOrderProduct(Order order, Long productId) {
        return OrderProduct.builder()
                .order(order)
                .productId(productId)
                .build();
    }
}