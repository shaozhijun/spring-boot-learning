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
		System.out.println("��Ҫ������Ϣ��...");
		redisTemplate.convertAndSend("message", "��ӭʹ��redis����Ϣ����!");
        try {
        //������Ϣ���ӵȴ���
            System.out.println("��Ϣ���ڷ���...");
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("��Ϣ����ʧ��...");
        }
	}
}
