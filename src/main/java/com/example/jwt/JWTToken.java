package com.example.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author Yongdong Jin
 * @version 1.0
 * @since <pre>2018/4/19 18:11</pre>
 */
public class JWTToken {
    private static final String SECRET = "fuck";

    public static String createToke(Map<String, Object> payload) throws UnsupportedEncodingException {
        //签发时间
        LocalDateTime now = LocalDateTime.now();

        //过期时间 - 1分钟过期
        LocalDateTime expire = now.plus(1, ChronoUnit.MINUTES);

        //token三部分：head 、payload 、signature
        JWTCreator.Builder builder = JWT.create()
                .withExpiresAt(Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()))
                .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        //payload
        payload.forEach((key, value) -> {
            if (value instanceof Date) {
                builder.withClaim(key, (Date) value);
            } else if (value instanceof Boolean) {
                builder.withClaim(key, (Boolean) value);
            } else if (value instanceof String) {
                builder.withClaim(key, (String) value);
            } else if (value instanceof Long) {
                builder.withClaim(key, (Long) value);
            } else if (value instanceof Integer) {
                builder.withClaim(key, (Integer) value);
            } else if (value instanceof Double) {
                builder.withClaim(key, (Double) value);
            }
        });
        //sign默认会添加两个head tpy 、alg
        return builder.sign(Algorithm.HMAC256(SECRET));

    }

    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            return verify.getClaims();
        } catch (Exception e) {
            throw new RuntimeException("Token检验失败" + e.getMessage());
        }
    }
}
