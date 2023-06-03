package com.bistu.why.cart.controller;

import java.util.Arrays;
import java.util.List;

import com.bistu.why.cart.anno.MustLoginAnno;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;
import com.bistu.why.model.cart.CartEntity;
import com.bistu.why.model.dto.CartDto;
import com.bistu.why.service.cart.CartService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * @author why
 * @email bistu.com
 * @date 2023-05-29 16:47:48
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //一键下单
    @PostMapping("/toPay")
    @MustLoginAnno
    public R toPay(List<CartDto> cartDtos) {
        return cartService.toPay(cartDtos);
    }

    //添加购物车
    @PostMapping("/addCart")
    public R addCart(@RequestBody CartDto cartDto, HttpSession httpSession) {
        return cartService.addCart(cartDto, httpSession);
    }


    //展示购物车
    @GetMapping("/list/{current}/{size}")
    public R list(@PathVariable("current") int curren, @PathVariable("size") int size, HttpSession httpSession) {
        PageUtils page = cartService.queryPage(curren, size, httpSession);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{cartId}")
    public R info(@PathVariable("cartId") Integer cartId) {
        CartEntity cart = cartService.getById(cartId);
        return R.ok().put("cart", cart);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("stock:cart:save")
    public R save(@RequestBody CartEntity cart) {
        cartService.save(cart);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("stock:cart:update")
    public R update(@RequestBody CartEntity cart) {
        cartService.updateById(cart);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("stock:cart:delete")
    public R delete(@RequestBody Integer[] cartIds) {
        cartService.removeByIds(Arrays.asList(cartIds));

        return R.ok();
    }

}
