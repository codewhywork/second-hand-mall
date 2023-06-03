package com.bistu.why.model.cart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author why
 * @email bistu.com
 * @date 2023-05-29 16:47:48
 */
@Data
@TableName("second_hand_cart")
public class CartEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO,value = "1")
    private Integer cartId;

    private Long skuId;

    private Integer count;

    private String userId;

}
