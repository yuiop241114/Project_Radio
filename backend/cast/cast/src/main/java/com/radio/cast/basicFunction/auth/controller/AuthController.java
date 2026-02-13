package com.radio.cast.basicFunction.auth.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
    //로그인 요청 후 accessToken, RefreshTokne 발급
    LoginResponse loginResponse = authService.login(loginRequest);
    
    //RefreshToken Redis에 저장
    refreshTokenService.saveRefreshToken(loginRequest.getEmail(), loginResponse.getRefreshToken(), jwtUtil.refreshExpiration);

    //HttpOnly Cookie에 RefreshToken 저장
    ResponseCookie cookie = ResponseCookie.from("refreshToken", loginResponse.getRefreshToken())
            .httpOnly(true)
            .secure(false) // 배포시 https면 true
            .sameSite("Lax")   //secure를 true일때 사용
            .path("/")
            .maxAge(jwtUtil.refreshExpiration / 1000)
            .build();

    response.addHeader("Set-Cookie", cookie.toString());

    //RefreshToken은 이제 바디에서 제거 (보안)
    loginResponse.setRefreshToken(null);

    SignUpResponse user = new SignUpResponse( authService.userData(loginRequest.getEmail()) );
    loginResponse.setId(user.getId());
    loginResponse.setEmail(user.getEmail());
    loginResponse.setUsername(user.getUsername());

    //accessToken, RefreshToken 반환
    return ResponseEntity.ok(loginResponse);
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
   * @return
   */
  @PostMapping("/refreshRT")
  public ResponseEntity<String> postMethodName(HttpServletRequest request, HttpServletResponse response) {
   System.out.println("쿠키 확인 시작");
   Cookie[] cookies = request.getCookies();
   System.out.println("cookies = " + cookies);
   try {

        // 쿠키에서 refreshToken 꺼내기
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("RefreshToken 없음");
        }

        //토큰에서 username 추출
        String username = jwtUtil.getUsernameFromToken(refreshToken);

        //Redis에 저장된 토큰 조회
        String savedToken = refreshTokenService.getRefreshToken(username);

        if (savedToken == null || !savedToken.equals(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("유효하지 않은 토큰입니다");
        }

        //RefreshToken 재발급 및 저장
        String newRefreshToken = jwtUtil.generateRefreshToken(username);
        refreshTokenService.saveRefreshToken(
                username,
                newRefreshToken,
                jwtUtil.refreshExpiration
        );

        //쿠키에 RefreshToken 재저장
        ResponseCookie newCookie = ResponseCookie.from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtUtil.refreshExpiration / 1000)
                .build();

        response.addHeader("Set-Cookie", newCookie.toString());

        //새 AccessToken 발급
        String newAccessToken = jwtUtil.generateToken(username);

        return ResponseEntity.ok(newAccessToken);

    } catch (ExpiredJwtException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("RefreshToken 만료");

    } catch (Exception e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("토큰 오류");
    }
  }
  
}
