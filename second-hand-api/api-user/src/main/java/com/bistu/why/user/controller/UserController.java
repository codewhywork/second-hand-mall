package com.bistu.why.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.bistu.why.model.user.UserEntity;
import com.bistu.why.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.common.result.R;
import com.bistu.why.common.utils.PageUtils;



/**
 * 
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-25 16:52:10
 */
@RestController
@RequestMapping("/user")
@Api
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{userId}")
    public R info(@PathVariable("userId") Integer userId){
		UserEntity user = userService.getById(userId);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] userIds){
		userService.removeByIds(Arrays.asList(userIds));
        return R.ok();
    }

}
