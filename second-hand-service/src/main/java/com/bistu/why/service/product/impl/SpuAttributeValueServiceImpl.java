package com.bistu.why.service.product.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bistu.why.model.product.AttributeAttrgroupRelationEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.Query;



import com.bistu.why.dao.product.SpuAttributeValueDao;
import com.bistu.why.model.product.
SpuAttributeValueEntity;
import com.bistu.why.service.product.SpuAttributeValueService;


@Service("spuAttributeValueService")
public class SpuAttributeValueServiceImpl extends ServiceImpl<SpuAttributeValueDao, SpuAttributeValueEntity> implements SpuAttributeValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Query query = new Query(params);
        IPage<SpuAttributeValueEntity> page = this.page(
                new Page<>(query.getPage(), query.getLimit()),
                new QueryWrapper<>()
        );

        return new PageUtils(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }
}