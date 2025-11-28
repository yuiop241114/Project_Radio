package com.radio.cast.globalFile.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.radio.cast.basicFunction.etc.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /* 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // CSRF 비활성화 (REST API인 경우)
            http.csrf(AbstractHttpConfigurer::disable)
            // 세션 정책 설정 (JWT 사용 시 STATELESS)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 인증/인가 설정
            .authorizeHttpRequests(auth -> auth
                    // Swagger 관련 경로 모두 허용
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/api-docs/**",
                            "/api/**"
                    ).permitAll()
                    // 공개 API 경로 허용 (로그인, 회원가입 등)
                    .requestMatchers(
                            //추후 기능 개발 후 경로 추가
                            "/api/auth/**"
                    ).permitAll()
                    
                    // 그 외 모든 요청은 인증 필요
                    .anyRequest().authenticated()
            );

        return http.build();
    }
    */
    private final JwtFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    // 1) 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) 사용자 인증 제공자 (UserDetailsService + PasswordEncoder)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 3) AuthenticationManager 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 4) CORS 설정 (React 연동 필요)
    @Bean
    public UrlBasedCorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3000")); // React 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 5) SecurityFilterChain (JWT 인증의 핵심)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // JWT 사용 시 CSRF 비활성화
            .cors(cors -> cors.configurationSource(corsConfig())) // CORS 적용
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함 (JWT)
            .authorizeHttpRequests(auth -> auth
                    // 인증 없이 접근 가능한 경로
                    .requestMatchers("/auth/login", "/auth/register").permitAll()
                    // 정적 파일 허용
                    .requestMatchers("/", "/favicon.ico").permitAll()
                    // 나머지는 인증 필요
                    .anyRequest().authenticated()
            )
            // Provider 설정
            .authenticationProvider(authenticationProvider())
            // JWT 필터를 UsernamePasswordAuthenticationFilter 이전에 배치
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
