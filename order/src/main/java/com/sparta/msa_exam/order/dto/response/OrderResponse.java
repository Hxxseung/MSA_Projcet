package com.sparta.msa_exam.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class OrderResponse implements Serializable {
    private Long orderId;
    private String name;
    private List<Long> products;
}