package com.ead.notificationhex.adapters.configs.security;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private UUID userId;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(String userId, String roles) {
        List<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(s -> new SimpleGrantedAuthority(s))
                .collect(Collectors.toList());

       return new UserDetailsImpl(UUID.fromString(userId), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return null; }

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
