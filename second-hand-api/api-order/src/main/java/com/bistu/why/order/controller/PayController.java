package com.bistu.why.order.controller;

import com.bistu.why.common.renren.utils.R;
import com.bistu.why.model.dto.SaveOrderDto;
import com.bistu.why.order.pay.payContext;
import com.bistu.why.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("order/order")
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    payContext payContext;

    @PostMapping("/save")
    public String save(@RequestBody SaveOrderDto saveOrderDto) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ExecutionException, InterruptedException {
        orderService.save(saveOrderDto);
        return payContext.toPayHtml(saveOrderDto.getPayType());
    }
}
