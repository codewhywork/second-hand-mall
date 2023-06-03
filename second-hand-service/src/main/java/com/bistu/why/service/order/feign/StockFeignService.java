package com.bistu.why.service.order.feign;

import com.bistu.why.common.renren.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author why
 */
//@FeignClient("second-hand-mall-stock")
//@Component
//public interface StockFeignService {
//    @GetMapping("/subcount/{skuId}/{salecount}")
//    public int subcount(@PathVariable("skuId") String skuId, @PathVariable("salecount") int salecount);
//}
