package com.bistu.why.order.pay.impl;

import com.bistu.why.order.pay.payWay;

/**
 * @author why
 */
public class wxPay implements payWay {
    @Override
    public String doPay() {
        //返回微信支付页面
        return "wxPay";
    }
}
