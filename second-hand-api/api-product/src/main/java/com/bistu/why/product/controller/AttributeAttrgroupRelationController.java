package com.bistu.why.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.model.product.
AttributeAttrgroupRelationEntity;
import com.bistu.why.service.product.AttributeAttrgroupRelationService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;



/**
 * 属性&属性分组关联
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@RestController
@RequestMapping("product/attributeattrgrouprelation")
public class AttributeAttrgroupRelationController {
    @Autowired
    private AttributeAttrgroupRelationService attributeAttrgroupRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attributeAttrgroupRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AttributeAttrgroupRelationEntity attributeAttrgroupRelation = attributeAttrgroupRelationService.getById(id);
        return R.ok().put("attributeAttrgroupRelation", attributeAttrgroupRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttributeAttrgroupRelationEntity attributeAttrgroupRelation){
		attributeAttrgroupRelationService.save(attributeAttrgroupRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttributeAttrgroupRelationEntity attributeAttrgroupRelation){
		attributeAttrgroupRelationService.updateById(attributeAttrgroupRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		attributeAttrgroupRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
