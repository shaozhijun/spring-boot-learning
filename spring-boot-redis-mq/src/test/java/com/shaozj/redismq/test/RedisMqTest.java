package com.shaozj.redismq.test;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisMqTest extends BaseTest{

	@Autowired
    CountDownLatch latch;
    @Autowired
    StringRedisTemplate redisTemplate;
	
    @Test
	public void testRedis() {
		System.out.println("我要发送消息咯...");
		redisTemplate.convertAndSend("message", "欢迎使用redis的消息队列!");
        try {
        //发送消息连接等待中
            System.out.println("消息正在发送...");
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("消息发送失败...");
        }
	}
}
