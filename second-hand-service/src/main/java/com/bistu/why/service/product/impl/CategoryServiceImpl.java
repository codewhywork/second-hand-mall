package com.bistu.why.service.product.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bistu.why.common.constant.RedisConstant;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.Query;


import com.bistu.why.dao.product.CategoryDao;
import com.bistu.why.model.product.
        CategoryEntity;
import com.bistu.why.service.product.CategoryService;


/**
 * @author why
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Query query = new Query(params);
        IPage<CategoryEntity> page = this.page(
                new Page<>(query.getPage(), query.getLimit()),
                new QueryWrapper<>()
        );

        return new PageUtils(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public List<CategoryEntity> listTree() {
        RLock lock = redissonClient.getLock(RedisConstant.CATEGORY_LIST_LOCK_KET_PREFIX);
        try {
            lock.lock();
            //获取所有分类
            List<CategoryEntity> list = this.list();
            //所有一级分类
            List<CategoryEntity> parent = list.stream().filter(c -> c.getParentCid() == 0).collect(Collectors.toList());
            //找到一级分类下的所有分类
            return parent.stream().peek(p -> p.setChildren(getCategoryChildren(p, list))).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    static List<CategoryEntity> getCategoryChildren(CategoryEntity parent, List<CategoryEntity> list) {
        return list.stream().filter(c -> Objects.equals(c.getParentCid(), parent.getCatId()))
                .peek(c -> c.setChildren(getCategoryChildren(c, list))).collect(Collectors.toList());
    }
}