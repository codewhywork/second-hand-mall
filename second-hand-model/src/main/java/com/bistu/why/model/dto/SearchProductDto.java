package com.bistu.why.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author why
 */
@Data
public class SearchProductDto {

    private String spuName;

    private String spuDescription;

    private String sort;

    private String introduce;

    private BigDecimal price;

    private Long saleCount;
}
