package com.bistu.why.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.model.product.
AttributeGroupEntity;
import com.bistu.why.service.product.AttributeGroupService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;



/**
 * 属性分组
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@RestController
@RequestMapping("product/attributegroup")
public class AttributeGroupController {
    @Autowired
    private AttributeGroupService attributeGroupService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attributeGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttributeGroupEntity attributeGroup = attributeGroupService.getById(attrGroupId);

        return R.ok().put("attributeGroup", attributeGroup);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttributeGroupEntity attributeGroup){
		attributeGroupService.save(attributeGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttributeGroupEntity attributeGroup){
		attributeGroupService.updateById(attributeGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attributeGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
