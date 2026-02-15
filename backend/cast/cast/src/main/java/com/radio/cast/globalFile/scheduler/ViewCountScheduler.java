package com.radio.cast.globalFile.scheduler;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.radio.cast.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ViewCountScheduler {
  private final RedisTemplate<String, String> redisTemplate;
  private final PostRepository postRepository;

  @Scheduled(fixedRate = 300000)//5분마다 스케줄러 실행
  public void viewCount(){
    Set<String> keys = redisTemplate.keys("post:view:*");
      if (keys == null) return;
      for (String key : keys) {
        if (key.contains("ip")) continue;

        Long postId = Long.parseLong(key.split(":")[2]);

        String value = redisTemplate.opsForValue().get(key);
        if (value == null) continue;

        int viewCount = Integer.parseInt(value);

        // postRepository.updateViewCount(postId, viewCount);
    }
  }
}
