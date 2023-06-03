package com.bistu.why.order.pay;

import com.bistu.why.order.factory.payFactory;
import org.springframework.stereotype.Component;

/**
 * @author why
 */
@Component
public class payContext {
    public String toPayHtml(String payType) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (payType == null) {
            return "404.html";
        }
        payWay payStrategy = payFactory.getPayType(payType);
        if (payStrategy != null) {
            return payStrategy.doPay();
        } else {
            return "不存在支付接口";
        }
    }
}
