package com.bistu.why.service.stock;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.stock.StockEntity;

import java.util.Map;

/**
 * 库存表，代表库存，秒杀库存等信息
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-29 15:39:35
 */
public interface StockService extends IService<StockEntity> {
    PageUtils queryPage(Map<String, Object> params);

    int subcount(Long skuId, int salecount);
    int addcount(Long skuId, int salecount);
}

