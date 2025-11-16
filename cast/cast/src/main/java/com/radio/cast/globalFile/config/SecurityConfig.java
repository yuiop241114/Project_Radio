package com.radio.cast.globalFile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // CSRF 비활성화 (REST API인 경우)
            http.csrf(AbstractHttpConfigurer::disable)
            
            // 세션 정책 설정 (JWT 사용 시 STATELESS)
            .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 인증/인가 설정
            .authorizeHttpRequests(auth -> auth
                    // Swagger 관련 경로 모두 허용
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/api-docs/**"
                    ).permitAll()
                    
                    // 공개 API 경로 허용 (로그인, 회원가입 등)
                    .requestMatchers(
                            "/api/auth/**",
                            "/api/public/**"
                    ).permitAll()
                    
                    // 그 외 모든 요청은 인증 필요
                    .anyRequest().authenticated()
            );

        return http.build();
    }
}
