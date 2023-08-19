package com.ead.notificationhex.adapters.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {
    @Value("${ead.jwt_secret}")
    private String jwtSecret;

    public String getClaimNameJwt(String token, String name) {
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getClaim(name).asString();
    }
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getSubject();
    }

}
