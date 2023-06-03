package com.bistu.why.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.Query;
import com.bistu.why.model.product.BrandEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-27 12:54:01
 */
@Mapper
public interface BrandDao extends BaseMapper<BrandEntity> {
	
}
