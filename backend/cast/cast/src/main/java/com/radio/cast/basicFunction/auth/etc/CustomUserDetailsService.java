package com.radio.cast.basicFunction.auth.etc;

import java.lang.StackWalker.Option;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.radio.cast.basicFunction.user.dto.SignUpResponse;
import com.radio.cast.basicFunction.user.entity.User;
import com.radio.cast.basicFunction.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SignUpResponse userData = userRepository.findByEmail(email).get();

        User user = userRepository.findByUsername(userData.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("해당 하는 사용자를 찾을 수 없음"));
        
        return new CustomUserDetails(user);
    }

}
