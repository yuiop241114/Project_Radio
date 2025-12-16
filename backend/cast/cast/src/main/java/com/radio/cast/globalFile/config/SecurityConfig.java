package com.radio.cast.globalFile.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.radio.cast.basicFunction.auth.etc.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    
    private final JwtFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //사용자 인증 제공자 (UserDetailsService + PasswordEncoder)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    //AuthenticationManager 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //SecurityFilterChain (JWT 인증의 핵심)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // JWT 사용 시 CSRF 비활성화
            .cors(withDefaults()) // CORS설정을 외부 파일에서 따로 Bean으로 설정해서 withDefault() 메소드만 작성하면 Spring boot에서 알아서 찾아서 실행함
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함 (JWT)
            .authorizeHttpRequests(auth -> auth
                    // 인증 없이 접근 가능한 경로
                    .requestMatchers("/auth/login", "/user/signUp").permitAll()
                    //swagger 관련 링크 허용
                    .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui.html",
                                "/api-docs/**"
                        ).permitAll()
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
