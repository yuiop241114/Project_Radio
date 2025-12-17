package com.radio.cast.basicFunction.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.radio.cast.basicFunction.auth.dto.LoginRequest;
import com.radio.cast.globalFile.config.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public String login(LoginRequest loginRequest){
    //아이디, 비밀번호 기반 인증 토큰 생성
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

    //AuthenticationManager(Spring Security 인증)에게 인증 요청
    Authentication authentication = authenticationManager.authenticate(authToken);

    //인증 성공 후 JWT 발급
    String token = jwtUtil.generateToken(authentication.getName());

    return token;
  }
}
