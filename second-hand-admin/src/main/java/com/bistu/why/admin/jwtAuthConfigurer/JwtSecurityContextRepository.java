package com.bistu.why.admin.jwtAuthConfigurer;

import com.alibaba.fastjson2.JSON;
import com.bistu.why.common.constant.JwtConstant;
import com.bistu.why.common.constant.RedisConstant;
import com.bistu.why.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.bistu.why.common.constant.RedisConstant.AUTHENTICATION_CACHE_KEY_PREFIX;

/**
 * @author why
 */
@Component
public class JwtSecurityContextRepository implements SecurityContextRepository {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private final static String ACCESS_TOKEN = "accessToken";

    /*验证请求头 是否携带验证 jwt 放在contextholder*/
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        String jwt = requestResponseHolder.getRequest().getHeader(JwtConstant.JWT_HEADER);
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        if (StringUtils.isEmpty(jwt)) {
            return emptyContext;
        }
        String[] split = jwt.split("#");
        if (JwtConstant.JWT_AUTH_TYPE.equalsIgnoreCase(split[0])) {
            ObjectInputStream ois = null;
            Claims claims = null;
            try {
                claims = JwtUtil.parserJwt(split[1]);
                String uuid = (String) claims.get("data");
                String obj = stringRedisTemplate.opsForValue().get(AUTHENTICATION_CACHE_KEY_PREFIX + uuid);
                if (obj == null) {
                    requestResponseHolder.getResponse().getOutputStream().write("您的登录已失效".getBytes(StandardCharsets.UTF_8));
                    return emptyContext;
                }
                ByteArrayInputStream byteIn = new ByteArrayInputStream(obj.getBytes(StandardCharsets.ISO_8859_1));
                ois = new ObjectInputStream(byteIn);
                emptyContext.setAuthentication((Authentication) ois.readObject());
                return emptyContext;
            } catch (JwtException | IOException | ClassNotFoundException e) {

                throw new RuntimeException(e);
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return emptyContext;
    }

    /*认证成功之后保存*/
    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        //随机uui
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String jwt = JwtUtil.createJwt(uuid);

        request.setAttribute(ACCESS_TOKEN, jwt);
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = null;
        try {
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(context.getAuthentication());
            String str = byteOut.toString("ISO-8859-1");//此处只能是ISO-8859-1,但是不会影响中文使用
            opsForValue.set(AUTHENTICATION_CACHE_KEY_PREFIX + uuid, str, RedisConstant.REDIS_EXPIRATION_KET_PREFIX, TimeUnit.SECONDS);
            Cookie cookie = new Cookie(JwtConstant.JWT_HEADER, JwtConstant.JWT_AUTH_TYPE + "#" + jwt);
            cookie.setMaxAge(JwtConstant.JWT_EXPIRATION);
            cookie.setDomain("secondhand.com");
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (IOException e) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("400","未登录");
            byte[] datas = JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
            response.setContentType("application/json");
            try {
                response.getOutputStream().write(datas);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        //TODO 解决重复登陆 key过多 删除之前的key
    }

    /*校验jwt信息*/
    @Override
    public boolean containsContext(HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.JWT_HEADER);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        Boolean hasKey = this.redisTemplate.hasKey(AUTHENTICATION_CACHE_KEY_PREFIX + token);
        if (hasKey != null) {
            return hasKey;
        }
        return false;
    }
}
