package com.bistu.why.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.model.product.
SpuAttributeValueEntity;
import com.bistu.why.service.product.SpuAttributeValueService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;



/**
 * spu属性值
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@RestController
@RequestMapping("product/spuattributevalue")
public class SpuAttributeValueController {
    @Autowired
    private SpuAttributeValueService spuAttributeValueService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuAttributeValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuAttributeValueEntity spuAttributeValue = spuAttributeValueService.getById(id);

        return R.ok().put("spuAttributeValue", spuAttributeValue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SpuAttributeValueEntity spuAttributeValue){
		spuAttributeValueService.save(spuAttributeValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuAttributeValueEntity spuAttributeValue){
		spuAttributeValueService.updateById(spuAttributeValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("product:spuattributevalue:delete")
    public R delete(@RequestBody Long[] ids){
		spuAttributeValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
