package com.radio.cast.basicFunction.etc;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.radio.cast.basicFunction.entity.User;

public class CustomUserDetails implements UserDetails{

    private final User user;

     public CustomUserDetails(User user) { 
        this.user = user; 
    }

    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 역할(권한) 필요 없으면 빈 리스트로
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return java.util.List.of();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
