package com.radio.cast.basicFunction.auth.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RedisTemplate<String, String> redisTemplate;

  public void saveRefreshToken(String username, String refreshToken, long expiration){
    String key = "RT:" + username;
    redisTemplate.opsForValue().set(key, refreshToken, expiration, TimeUnit.MILLISECONDS);
  }

  public String getRefreshToken(String username){
    return redisTemplate.opsForValue().get("RT:" + username);
  }

  public void deleteRefreshToken(String username){
    redisTemplate.delete("RT:" + username);
  }
}
