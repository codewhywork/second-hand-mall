package com.bistu.why.dao.product;


import com.bistu.why.model.product.
CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
