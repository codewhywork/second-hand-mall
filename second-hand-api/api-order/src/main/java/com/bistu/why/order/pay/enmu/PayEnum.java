package com.bistu.why.order.pay.enmu;

import lombok.Data;
import lombok.Getter;

/**
 * @author why
 */
@Getter
public enum PayEnum {
    wxPay(1, "com.bistu.why.order.pay.impl.wxPay"),
    zfbPay(2, "com.bistu.why.order.pay.impl.zfbPay");
    private final Integer payWay;
    private final String classPath;

    PayEnum(Integer payWay, String classPath) {
        this.payWay = payWay;
        this.classPath = classPath;
    }
}
