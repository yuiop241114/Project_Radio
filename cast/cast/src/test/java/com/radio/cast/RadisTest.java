package com.radio.cast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RadisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void redisTest() {
        // Ping 테스트
        String pong = redisTemplate.getConnectionFactory()
                                   .getConnection()
                                   .ping();

        System.out.println("Redis Ping Response: " + pong);

        assertThat(pong).isEqualTo("PONG");
    }
}
