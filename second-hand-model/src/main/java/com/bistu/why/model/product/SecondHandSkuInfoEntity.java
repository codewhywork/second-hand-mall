package com.bistu.why.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * sku信息
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@Data
@TableName("second_hand_sku_info")
public class SecondHandSkuInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * skuId
     */
    @TableId(type = IdType.AUTO)
    private Long skuId;
    /**
     * spuId
     */
    private Long spuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 所属分类id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 默认图片
     */
    private String skuDefaultImg;
    /**
     * 标题
     */
    private String skuTitle;
    /**
     * 副标题
     */
    private String skuSubtitle;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 销量
     */
    private Long saleCount;

    @TableField(exist = false)
    private List<SkuSaleAttributeValueEntity> skuSaleAttributeValueEntity;
}
