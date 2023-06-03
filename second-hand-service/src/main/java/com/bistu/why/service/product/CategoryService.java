package com.bistu.why.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.product.
CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listTree();
}

