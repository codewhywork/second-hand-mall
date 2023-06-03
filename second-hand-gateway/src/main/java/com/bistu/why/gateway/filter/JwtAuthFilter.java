package com.bistu.why.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.common.utils.StringUtils;
import com.bistu.why.common.constant.JwtConstant;
import com.bistu.why.common.constant.RedisConstant;
import com.bistu.why.common.utils.JwtUtil;
import com.bistu.why.gateway.contextHolder.UserThreadLocal;
import com.bistu.why.gateway.threadlocalVo.UserAuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author why
 */
@Order(0)
@Component
public class JwtAuthFilter implements GlobalFilter {

    @Resource
    RedisTemplate<String, Object> redisTemplate;
    /*
     * 用来验证jwt的filter
     * 规则：
     * 随机生成的 uuid 作为 redis的key 然后把用户信息作为value
     * 随机生成的 uuid 作为 jwt.payload部分token
     * */

    private final String[] ALLOW_URL = {
            "/user/reg",
            "/user/doLogin",
            "/user/vc.jpg",
            "/admin/**",
            "/product/spuinfo/**",
            "/user/login.html",
            "/favicon.ico",
            "/cart/list",
            "/cart/addCart"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        String requestURI = String.valueOf(request.getPath());
//        //允许访问的路径
//        for (String uri : ALLOW_URL) {
//            if (StringUtils.contains(uri, requestURI)) {
//                return chain.filter(exchange);
//            }
//        }
//        List<String> list = request.getHeaders().get(JwtConstant.JWT_HEADER);
//        if (list == null || list.isEmpty()) {
//            Map<String, String> data = new HashMap<>();
//            data.put("code", "400");
//            data.put("msg", HttpStatus.UNAUTHORIZED.getReasonPhrase());
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return packData(response, data);
//        }
        return chain.filter(exchange);
    }


    private static Mono<Void> packData(ServerHttpResponse response, Map<String, String> data) {
        byte[] datas = JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(datas);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
