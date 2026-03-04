package com.radio.cast.globalFile.config;

import java.util.List;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class JwtHandshakeInterceptor implements HandshakeInterceptor{
  
  private final JwtUtil jwtUtil;

  public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                  ServerHttpResponse response,
                                  WebSocketHandler wsHandler,
                                  Map<String, Object> attributes) {

      List<String> authHeader = request.getHeaders().get("Authorization");

      if (authHeader != null && !authHeader.isEmpty()) {
          String token = authHeader.get(0).replace("Bearer ", "");

          if (jwtUtil.validateToken(token)) {
              String username = jwtUtil.getUsernameFromToken(token);
              attributes.put("username", username);
              return true;
          }
      }
      return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                              ServerHttpResponse response,
                              WebSocketHandler wsHandler,
                              Exception exception) {
        // 특별히 처리할 로직이 없기 때문에 메소드 껍데기만 구현
    }
  }
