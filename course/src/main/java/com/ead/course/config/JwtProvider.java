package com.ead.course.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtProvider {
    @Value("${ead.jwt_secret}")
    private String jwtSecret;

    public String getSubject(String token) {
        return JWT
                .require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getSubject();
    }

    public String getAuthorities(String token) {
        return JWT
                .require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getClaim("roles").asString();
    }
}
