package com.bistu.why.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.Query;
import com.bistu.why.dao.product.BrandDao;
import com.bistu.why.model.product.BrandEntity;
import com.bistu.why.model.product.SkuImagesEntity;
import com.bistu.why.service.product.BrandService;
import org.springframework.stereotype.Service;
import java.util.Map;



@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Query query = new Query(params);
        IPage<BrandEntity> page = this.page(
                new Page<>(query.getPage(), query.getLimit()),
                new QueryWrapper<>()
        );
        return new PageUtils(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }
}