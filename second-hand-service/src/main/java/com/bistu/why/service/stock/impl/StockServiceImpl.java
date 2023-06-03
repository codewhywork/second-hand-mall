package com.bistu.why.service.stock.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.Query;
import com.bistu.why.dao.stock.StockDao;
import com.bistu.why.model.stock.StockEntity;
import com.bistu.why.service.stock.StockService;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * @author why
 */
@Service("stockService")
public class StockServiceImpl extends ServiceImpl<StockDao, StockEntity> implements StockService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Query query = new Query(params);
        IPage<StockEntity> page = this.page(
                new Page<>(query.getPage(), query.getLimit()),
                new QueryWrapper<>()
        );
        return new PageUtils(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public int subcount(Long skuId, int salecount) {
        return this.baseMapper.subcount(skuId, salecount);
    }

    @Override
    public int addcount(Long skuId, int salecount) {
        return this.baseMapper.addcount(skuId, salecount);
    }

}