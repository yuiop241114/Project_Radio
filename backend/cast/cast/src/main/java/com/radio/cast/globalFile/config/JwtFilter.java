package com.radio.cast.globalFile.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.radio.cast.basicFunction.auth.etc.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
    
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1) Authorization 헤더에서 토큰 꺼내기
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // "Bearer " 제거
            username = jwtUtil.getUsernameFromToken(token);
        }

        // 2) username이 있고 현재 SecurityContext에 인증이 없으면 인증 처리 진행
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 3) DB에서 유저 로딩
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // 4) 토큰 유효성 검증
            if (jwtUtil.validateToken(token)) {

                // 5) 인증 정보 생성 + SecurityContext 에 등록
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6) 다음 필터 계속 진행
        filterChain.doFilter(request, response);
    }

    
}
