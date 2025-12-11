package com.radio.cast.basicFunction.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.basicFunction.auth.dto.LoginRequest;
import com.radio.cast.basicFunction.auth.dto.LoginResponse;
import com.radio.cast.basicFunction.auth.dto.TokenRefresh;
import com.radio.cast.basicFunction.auth.service.AuthService;
import com.radio.cast.basicFunction.auth.service.RefreshTokenService;
import com.radio.cast.globalFile.config.JwtUtil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  
  private final AuthService authService;
  private final JwtUtil jwtUtil;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/login")
  /**
   * 로그인 메소드
   * @param loginRequest
   * @return
   */
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
    //로그인 요청 후 accessToken, RefreshTokne 발급
    String accessToken = jwtUtil.generateToken(loginRequest.getUesrname());
    String RefreshToken = jwtUtil.generateRefreshToken(loginRequest.getUesrname());
    
    //RefreshToken Redis에 저장
    refreshTokenService.saveRefreshToken(accessToken, RefreshToken, jwtUtil.expiration);
    //accessToken, RefreshToken 반환
    return ResponseEntity.ok(new LoginResponse(accessToken, RefreshToken));
  }
  
  @PostMapping("/refreshRT")
  public ResponseEntity<String> postMethodName(@RequestBody TokenRefresh tokenRefresh) {
    String refreshToken = tokenRefresh.getRefreshToken();
    String username = jwtUtil.getUsernameFromToken(refreshToken);

    String savedToken = refreshTokenService.getRefreshToken(username);

    //프론트에서 가져온 refreshToken을 검증
    if (!refreshToken.equals(savedToken)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("유효하지 않은 토큰입니다");
    }

    // 새 accessToken 발급
    String newAccessToken = jwtUtil.generateToken(username);
    return ResponseEntity.ok(newAccessToken);
  }
  
}
