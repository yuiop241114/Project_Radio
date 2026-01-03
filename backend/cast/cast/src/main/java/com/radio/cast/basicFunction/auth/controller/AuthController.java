package com.radio.cast.basicFunction.auth.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.basicFunction.auth.dto.LoginRequest;
import com.radio.cast.basicFunction.auth.dto.LoginResponse;
import com.radio.cast.basicFunction.auth.dto.TokenRefresh;
import com.radio.cast.basicFunction.auth.service.AuthService;
import com.radio.cast.basicFunction.auth.service.RefreshTokenService;
import com.radio.cast.basicFunction.user.dto.SignUpResponse;
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
    String accessToken = jwtUtil.generateToken(loginRequest.getEmail());
    String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getEmail());
    

    //RefreshToken Redis에 저장
    refreshTokenService.saveRefreshToken(loginRequest.getEmail(), refreshToken, jwtUtil.expiration);
    SignUpResponse user = authService.userData(loginRequest.getEmail()).get();
    //accessToken, RefreshToken 반환
    return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, user.getId(), user.getEmail(), user.getUsername()));
  }

  /**
   * 로그아웃 컨트롤러
   * @param authentication
   */
  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization){
    String token = authorization.substring(7);
    String email = jwtUtil.getUsernameFromToken(token);
    refreshTokenService.deleteRefreshToken(email);
    return ResponseEntity.ok("logout");
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
