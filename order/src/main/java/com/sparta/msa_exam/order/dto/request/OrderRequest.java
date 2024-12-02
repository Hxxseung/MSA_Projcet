package com.sparta.msa_exam.order.dto.request;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String name;
    private List<Long> productIds;
}