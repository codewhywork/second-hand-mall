package com.bistu.why.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.product.
SkuImagesEntity;

import java.util.Map;

/**
 * sku图片
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

