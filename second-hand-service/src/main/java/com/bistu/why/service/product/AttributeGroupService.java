package com.bistu.why.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.product.
AttributeGroupEntity;
import java.util.Map;

/**
 * 属性分组
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
public interface AttributeGroupService extends IService<AttributeGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

