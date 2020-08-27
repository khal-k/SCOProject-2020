package com.atguigu.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void TestRedisTemplate(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("apple", "red");
    }

    @Test
    public void TestExSet(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("banana", "yello", 5, TimeUnit.SECONDS);
    }



}
