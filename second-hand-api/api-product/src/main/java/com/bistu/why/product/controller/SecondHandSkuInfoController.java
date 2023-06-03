package com.bistu.why.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.model.product.SecondHandSkuInfoEntity;
import com.bistu.why.service.product.SecondHandSkuInfoService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;



/**
 * sku信息
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@RestController
@RequestMapping("product/secodehandskuinfo")
public class SecondHandSkuInfoController {
    @Autowired
    private SecondHandSkuInfoService secondHandSkuInfoService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = secondHandSkuInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId){
		SecondHandSkuInfoEntity secodeHandSkuInfo = secondHandSkuInfoService.getById(skuId);

        return R.ok().put("secodeHandSkuInfo", secodeHandSkuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SecondHandSkuInfoEntity secodeHandSkuInfo){
        secondHandSkuInfoService.save(secodeHandSkuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SecondHandSkuInfoEntity secodeHandSkuInfo){
        secondHandSkuInfoService.updateById(secodeHandSkuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] skuIds){
        secondHandSkuInfoService.removeByIds(Arrays.asList(skuIds));
        return R.ok();
    }

}
