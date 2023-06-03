package com.bistu.why.order.pay.impl;

import com.bistu.why.order.pay.payWay;

/**
 * @author why
 */
public class zfbPay implements payWay {
    @Override
    public String doPay() {
        //返回支付宝支付页面
        return "zfbPay";
    }
}
