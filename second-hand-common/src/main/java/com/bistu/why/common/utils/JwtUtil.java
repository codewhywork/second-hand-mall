package com.bistu.why.common.utils;

import com.bistu.why.common.constant.JwtConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class JwtUtil {
    public static String createJwt(String payload) {
        return createJwt(payload, null, null);
    }

    public static String createJwt(String payload, Integer calendar, Integer time) {
        if (calendar == null) {
            calendar = Calendar.SECOND;
        }
        if (time == null) {
            time = JwtConstant.JWT_EXPIRATION;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("data", payload);
        Calendar instance = Calendar.getInstance();
        instance.add(calendar, time);
        JwtBuilder jwtBuilder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setSubject("why")
                .setIssuedAt(new Date())
                .setExpiration(instance.getTime())
                .signWith(SignatureAlgorithm.HS256, JwtConstant.JWT_SECRET)
                .compressWith(new GzipCompressionCodec())
                .setClaims(hashMap);
        return jwtBuilder.compact();
    }

    public static Claims parserJwt(String compact) throws JwtException {
        return Jwts.parser().setSigningKey(JwtConstant.JWT_SECRET).parseClaimsJws(compact).getBody();
    }
}
