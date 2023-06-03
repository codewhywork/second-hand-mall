package com.bistu.why.utils;

import com.bistu.why.common.utils.HttpUtils;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author why
 */
@Component
@Slf4j
public class SendSmsUtils {
    @Value("${ali.appCode}")
    public String appCode;

    @Autowired
    public Producer producer;

    public String sendSms(String phone) {
        String host = "https://cdcxdxjk.market.alicloudapi.com";
        String path = "/chuangxin/dxjk";
        String method = "POST";
        String appcode = appCode;//开通服务后 买家中心-查看AppCode
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        String code = producer.createText();
        querys.put("content", "【创信】你的验证码是：" + code + "，3分钟内有效！");
        querys.put("mobile", phone);
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            log.info("sendSms：res{}", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
