package com.bistu.why.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.product.AttributeEntity;


import java.util.Map;

/**
 * 商品属性
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
public interface AttributeService extends IService<AttributeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

