package com.ead.course.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private List<GrantedAuthority> authorities;

    private UUID userId;

    public UserDetailsImpl(UUID userId, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    public static UserDetails build(String userId, String roles) {
        List<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return new UserDetailsImpl(UUID.fromString(userId),authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
