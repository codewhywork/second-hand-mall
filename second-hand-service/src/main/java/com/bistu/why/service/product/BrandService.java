package com.bistu.why.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.product.BrandEntity;


import java.util.Map;

/**
 * 
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-27 12:54:01
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

