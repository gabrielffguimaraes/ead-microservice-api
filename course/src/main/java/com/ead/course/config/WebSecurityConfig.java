package com.ead.course.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    public AuthenticationEntrypointImpl authenticationEntrypoint;

    @Autowired
    public AuthenticationJwtFilter authenticationJwtFilter;

    @Autowired
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntrypoint))
                .csrf().disable()
                .addFilterBefore(authenticationJwtFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
