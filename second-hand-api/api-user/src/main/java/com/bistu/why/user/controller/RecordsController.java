package com.bistu.why.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;
import com.bistu.why.contextHolder.UserThreadLocal;
import com.bistu.why.model.user.RecordsEntity;
import com.bistu.why.service.user.RecordsService;
import com.bistu.why.user.anno.MustLoginAnno;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author why
 * @email bistu.com
 * @date 2023-05-27 23:22:47
 */
@RestController
@RequestMapping("user/records")
public class RecordsController {
    @Autowired
    private RecordsService recordsService;


    @GetMapping("/getRecord")
    @MustLoginAnno
    public List<RecordsEntity> getRecord() {
        String userId = UserThreadLocal.geteUserThreadLocal().get().getUserId();
        return recordsService.list(new LambdaQueryWrapper<RecordsEntity>().eq(RecordsEntity::getUserId, userId));
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = recordsService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{recordsId}")
    public R info(@PathVariable("recordsId") String recordsId) {
        RecordsEntity records = recordsService.getById(recordsId);

        return R.ok().put("records", records);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody RecordsEntity records) {
        recordsService.save(records);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody RecordsEntity records) {
        recordsService.updateById(records);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] recordsIds) {
        recordsService.removeByIds(Arrays.asList(recordsIds));

        return R.ok();
    }

}
