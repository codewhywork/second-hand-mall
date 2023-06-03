package com.bistu.why.model.dto;

import lombok.Data;

@Data
public class CartDto {

    private Long skuId;

    private Integer count;

    private String userId;
}
