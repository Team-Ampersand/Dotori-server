package com.server.Dotori.util.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTest() {
        redisUtil.deleteData("Dotori");
        redisUtil.setData("Dotori", "Dotori-test");
        System.out.println(redisUtil.getData("Dotori"));
        Assertions.assertThat(redisUtil.getData("Dotori")).isEqualTo("Dotori-test");
    }
}