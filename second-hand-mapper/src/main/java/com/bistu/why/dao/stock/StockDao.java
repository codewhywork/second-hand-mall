package com.bistu.why.dao.stock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bistu.why.model.stock.StockEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 库存表，代表库存，秒杀库存等信息
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-29 15:39:35
 */
@Mapper
public interface StockDao extends BaseMapper<StockEntity> {
    int subcount(@Param("skuId") Long skuId, @Param("salecount") int salecount);

    int addcount(@Param("skuId") Long skuId, @Param("salecount") int salecount);
}
