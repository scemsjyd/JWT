package com.example.jwt;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) throws UnsupportedEncodingException {
        SpringApplication.run(JwtApplication.class, args);
        Map<String, Object> param = new HashMap<>();
        param.put("name", "张三");//payload
        param.put("age", 18);
        String token = JWTToken.createToke(param);
        System.out.println(token);
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5byg5LiJIiwiZXhwIjoxNTI0MTM1MzE5LCJpYXQiOjE1MjQxMzUzMTksImFnZSI6MTh9.mEcwseaI_4zt9s0rwR5ERCKcQjuxRYYpeD0dq2fL4x8";
        Map<String, Claim> stringClaimMap = JWTToken.verifyToken(token);
        System.out.println(stringClaimMap);
    }

}
