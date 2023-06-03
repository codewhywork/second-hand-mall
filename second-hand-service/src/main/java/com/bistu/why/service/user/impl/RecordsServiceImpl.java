package com.bistu.why.service.user.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.Query;
import com.bistu.why.dao.user.RecordsDao;
import com.bistu.why.service.user.RecordsService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bistu.why.model.user.RecordsEntity ;


@Service("recordsService")
public class RecordsServiceImpl extends ServiceImpl<RecordsDao, RecordsEntity> implements RecordsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Query query = new Query(params);
        IPage<RecordsEntity> page = this.page(
                new Page<>(query.getPage(), query.getLimit()),
                new QueryWrapper<>()
        );
        return new PageUtils(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

}