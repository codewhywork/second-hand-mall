package com.bistu.why.order.controller;

import java.util.Arrays;
import java.util.Map;

import com.bistu.why.common.renren.utils.R;
import com.bistu.why.model.order.OrderEntity;
import com.bistu.why.model.dto.SaveOrderDto;
import com.bistu.why.order.pay.payContext;
import com.bistu.why.service.order.OrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bistu.why.common.renren.utils.PageUtils;


/**
 * @author why
 * @email bistu.com
 * @date 2023-05-27 23:40:27
 */
@RestController
@RequestMapping("order/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{orderId}")
    public R info(@PathVariable("orderId") Long orderId) {
        OrderEntity order = orderService.getById(orderId);
        return R.ok().put("order", order);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody OrderEntity order) {
        orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("product:order:delete")
    public R delete(@RequestBody Long[] orderIds) {
        orderService.removeByIds(Arrays.asList(orderIds));

        return R.ok();
    }

}
