package com.radio.cast.basicFunction.auth.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RedisTemplate<String, String> redisTemplate;

  /**
   * Redis에 RefreshToken 저장 메소드
   * @param username
   * @param refreshToken
   * @param expiration
   */
  public void saveRefreshToken(String username, String refreshToken, long expiration){
    String key = "RT:" + username;
    redisTemplate.opsForValue().set(key, refreshToken, expiration, TimeUnit.MILLISECONDS);
  }

  /**
   * Redis에서 RefreshToken 가져오는 메소드
   * @param username
   * @return
   */
  public String getRefreshToken(String username){
    return redisTemplate.opsForValue().get("RT:" + username);
  }

  /**
   * Redis에서 RefreshToken 삭제 메소드
   * @param username
   */
  public void deleteRefreshToken(String username){
    redisTemplate.delete("RT:" + username);
  }
}
