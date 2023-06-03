package com.bistu.why.service.cart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;
import com.bistu.why.model.cart.CartEntity;
import com.bistu.why.model.dto.CartDto;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-29 16:47:48
 */
public interface CartService extends IService<CartEntity> {



    R addCart(CartDto cartDto, HttpSession httpSession);

    PageUtils queryPage(int curren, int size, HttpSession httpSession);

    R toPay(List<CartDto> cartDtos);
}

