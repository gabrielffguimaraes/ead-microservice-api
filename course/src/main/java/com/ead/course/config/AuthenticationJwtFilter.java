package com.ead.course.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    @Autowired
    public JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header.startsWith("Bearer")) {
            String token = getToken(header);
            UUID userId = jwtProvider.getSubject(token);
            List<GrantedAuthority> authorities = jwtProvider.getAuthorities(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }

    public String getToken(String header) {
        return header.substring(7);
    }
}
