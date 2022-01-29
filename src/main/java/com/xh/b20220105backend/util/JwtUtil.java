package com.xh.b20220105backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class JwtUtil {
    static String keys = "oahgnoix@666";

    public static String createToken(Integer time, Map<String, String> info) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, time);
        JWTCreator.Builder builder = JWT.create();
        for (Map.Entry<String, String> entry : info.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        builder.withExpiresAt(instance.getTime());
        String token = builder.sign(Algorithm.HMAC256(keys));
        return token;
    }
}
