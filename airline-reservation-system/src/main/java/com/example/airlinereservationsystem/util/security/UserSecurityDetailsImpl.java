package com.example.airlinereservationsystem.util.security;

import com.example.airlinereservationsystem.domain.User;
import com.example.airlinereservationsystem.domain.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UserSecurityDetailsImpl implements UserDetails {
    final Logger logger = LoggerFactory.getLogger(UserSecurityDetailsImpl.class);
    private String username;
    private String password;
    private List<UserRole> authorities;

    public UserSecurityDetailsImpl(User user) {
        username = user.getUsername();
        password = user.getPassword();
        authorities = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info("Authorities we got: " + authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
