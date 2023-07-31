package com.ead.course.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JwtProvider {
    public UUID getSubject(String token) {
        return null;
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        return null;
    }
}
