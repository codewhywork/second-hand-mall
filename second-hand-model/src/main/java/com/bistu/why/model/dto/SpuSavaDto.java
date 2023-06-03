package com.bistu.why.model.dto;

import com.bistu.why.model.product.AttributeEntity;
import com.bistu.why.model.product.SecondHandSkuInfoEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author why
 */
@Data
public class SpuSavaDto {
    private String spuName;

    private String spuDescription;

    private Long catalogId;

    private BigDecimal weight;

    private Integer publishStatus;

    private Date createTime;

    private Date updateTime;

    private String introduce;

    //spu图片
    private List<String> spuImages;

    //sku基本信息
    private List<SecondHandSkuInfoEntity> skuInfoEntities;

    private List<AttributeEntity> attributeEntities;
}
