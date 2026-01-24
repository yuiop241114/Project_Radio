package com.radio.cast.basicFunction.auth.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.radio.cast.basicFunction.auth.dto.LoginRequest;
import com.radio.cast.basicFunction.auth.dto.LoginResponse;
import com.radio.cast.basicFunction.user.dto.SignUpResponse;
import com.radio.cast.basicFunction.user.entity.User;
import com.radio.cast.basicFunction.user.repository.UserRepository;
import com.radio.cast.globalFile.config.JwtUtil;
import com.radio.cast.globalFile.exception.BusinessException;
import com.radio.cast.globalFile.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final BCryptPasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  public LoginResponse login(LoginRequest loginRequest){
    User user = userRepository.findByEmail(loginRequest.getEmail()).get();
    //비밀번호 검증
    if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
      throw new BusinessException(ErrorCode.INVALID_PASSWORD);
    }

    //아이디, 비밀번호 기반 인증 토큰 생성
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

    //AuthenticationManager(Spring Security 인증)에게 인증 요청
    Authentication authentication = authenticationManager.authenticate(authToken);

    //인증 성공 후 JWT 발급
    String accessToken = jwtUtil.generateToken(loginRequest.getEmail());
    String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getEmail());
    return new LoginResponse(accessToken, refreshToken, null, null, null);
  }

  public User userData(String email){
    return userRepository.findByEmail(email).get();
  }
}
