package com.bistu.why.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.bistu.why.model.product.SkuSaleAttributeValueEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.service.product.SkuSaleAttributeValueService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;



/**
 * sku销售属性&值
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@RestController
@RequestMapping("product/skusaleattributevalue")
public class SkuSaleAttributeValueController {
    @Autowired
    private SkuSaleAttributeValueService skuSaleAttributeValueService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuSaleAttributeValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SkuSaleAttributeValueEntity skuSaleAttributeValue = skuSaleAttributeValueService.getById(id);

        return R.ok().put("skuSaleAttributeValue", skuSaleAttributeValue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SkuSaleAttributeValueEntity skuSaleAttributeValue){
		skuSaleAttributeValueService.save(skuSaleAttributeValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SkuSaleAttributeValueEntity skuSaleAttributeValue){
		skuSaleAttributeValueService.updateById(skuSaleAttributeValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		skuSaleAttributeValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
