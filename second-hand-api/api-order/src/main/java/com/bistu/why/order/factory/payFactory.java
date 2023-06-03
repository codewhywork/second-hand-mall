package com.bistu.why.order.factory;

import com.bistu.why.order.pay.enmu.PayEnum;
import com.bistu.why.order.pay.payWay;

/**
 * @author why
 */
public class payFactory {
    public static payWay getPayType(String payType) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        PayEnum payEnum = PayEnum.valueOf(payType);
        if (payEnum == null) {
            return null;
        }
        return (payWay)Class.forName(payEnum.getClassPath()).newInstance();
    }
}
