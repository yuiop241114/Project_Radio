package com.radio.cast.globalFile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    
    @Bean
    public WebMvcConfigurer corsConfig(){

        //WebMvcConfigurer : 스프링 MVC 패턴에서 설정을 확장 할 수 있게 해주는 인터페이스
        //                   CORS, 리소스, 인터셉터, 포맷터 등 여러 설정을 커스터마이징 가능
        return new WebMvcConfigurer() {
            //일단 CORS만 설정 하기 떄문에 addCorsMappings만 오버라이딩 해서 커스텀 설정
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry){
                corsRegistry.addMapping("/**") //모든 API 경로 허가
                            //React 및 CRA(사용할 수도 있음) URL 허가 설정
                            .allowedOrigins("http://localhost:5173", "http://localhost:3000")
                            //허가 API 메소드 설정
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                            //API 요청 시 보내는 허가 헤더 설정
                            .allowedHeaders("*")
                            //쿠키/JWT/세션 기반 인증을 가능하게 함
                            //브라우저가 쿠키와 함께 요청을 보낼 수 있도록 허용하는 옵션
                            .allowCredentials(true);
            }
        };

    }
}
