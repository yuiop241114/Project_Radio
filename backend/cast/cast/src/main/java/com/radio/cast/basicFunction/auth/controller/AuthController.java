package com.radio.cast.basicFunction.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.basicFunction.auth.dto.LoginRequest;
import com.radio.cast.basicFunction.auth.dto.LoginResponse;
import com.radio.cast.basicFunction.auth.dto.TokenRefresh;
import com.radio.cast.basicFunction.auth.service.AuthService;
import com.radio.cast.basicFunction.auth.service.RefreshTokenService;
import com.radio.cast.globalFile.config.JwtUtil;

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
    String accessToken = jwtUtil.generateToken(loginRequest.getUsername());
    String RefreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername());
    
    //RefreshToken Redis에 저장
    System.out.println("회원아이디 " + loginRequest.getUsername());
    refreshTokenService.saveRefreshToken(loginRequest.getUsername(), RefreshToken, jwtUtil.expiration);
    //accessToken, RefreshToken 반환
    return ResponseEntity.ok(new LoginResponse(accessToken, RefreshToken));
  }

  @PostMapping("/logout")
  public void logout(Authentication authentication){
    String username = authentication.getName();
    refreshTokenService.deleteRefreshToken(username);
  }
  
  /**
   * RefreshToken 제발급 메소드
   * @param tokenRefresh
   * @return
   */
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
