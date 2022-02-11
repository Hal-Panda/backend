package com.xh.b20220105backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
@Component
public class JwtUtil {

    private static String keys="0000!aaaaasdnk2uh_d";
    private static Integer duringTime=600;

    public static String createToken(Map<String, String> info) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, duringTime);
        JWTCreator.Builder builder = JWT.create();
        for (Map.Entry<String, String> entry : info.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        builder.withExpiresAt(instance.getTime());
        String token = builder.sign(Algorithm.HMAC256(keys));
        return token;
    }

    public static String getTokenStringInfo(String token,String name) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            String str = decodedJWT.getClaim(name).asString();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static DecodedJWT verifyToken(String token) throws Exception {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(keys)).build();
        DecodedJWT verify = jwtVerifier.verify(token);

        return verify;
    }
}
