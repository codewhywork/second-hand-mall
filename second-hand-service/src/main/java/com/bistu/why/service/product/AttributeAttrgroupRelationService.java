package com.bistu.why.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.product.
AttributeAttrgroupRelationEntity;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
public interface AttributeAttrgroupRelationService extends IService<AttributeAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

