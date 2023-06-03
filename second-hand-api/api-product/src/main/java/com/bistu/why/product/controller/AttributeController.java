package com.bistu.why.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;

import com.bistu.why.model.product.AttributeEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.service.product.AttributeService;




/**
 * 商品属性
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@RestController
@RequestMapping("product/attribute")
public class AttributeController {
    @Autowired
    private AttributeService attributeService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attributeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttributeEntity attribute = attributeService.getById(attrId);

        return R.ok().put("attribute", attribute);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttributeEntity attribute){
		attributeService.save(attribute);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttributeEntity attribute){
		attributeService.updateById(attribute);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attributeService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
