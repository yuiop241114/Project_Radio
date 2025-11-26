package com.radio.cast.basicFunction.etc;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.radio.cast.basicFunction.entity.User;
import com.radio.cast.basicFunction.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserName(username).orElseThrow(
            () -> new UsernameNotFoundException("해당 하는 사용자를 찾을 수 없음"));
        
        return new CustomUserDetails(user);
    }

}
