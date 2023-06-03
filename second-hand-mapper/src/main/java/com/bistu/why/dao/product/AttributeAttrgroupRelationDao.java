package com.bistu.why.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bistu.why.model.product.AttributeAttrgroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 属性&属性分组关联
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@Mapper
public interface AttributeAttrgroupRelationDao extends BaseMapper<AttributeAttrgroupRelationEntity> {
	
}
