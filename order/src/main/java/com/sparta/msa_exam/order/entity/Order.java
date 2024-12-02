package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String name;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> productIds = new ArrayList<>();

    public void addProduct(OrderProduct orderProduct) {
        if (productIds == null) {
            productIds = new ArrayList<>();
        }
        productIds.add(orderProduct);
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        if (productIds == null) {
            productIds = new ArrayList<>();
        }
        productIds.addAll(orderProducts);
    }
}