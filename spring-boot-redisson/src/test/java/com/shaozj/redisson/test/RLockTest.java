package com.shaozj.redisson.test;

import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.shaozj.redisson.utils.RedissLockUtil;

public class RLockTest extends BaseTest {
	
	@Autowired
	RedissonClient redissonClient;
	Integer k = 0;
	
	@Test
	public void redisLockTest() {
		for (int i = 0; i < 100; i++) {
			RedissLockUtil.lock("test123");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("==============: " + k++);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            RedissLockUtil.unlock("test123");
           
        }
	}
	
	@Test
	public void test() {
//		redissonClient.gete
		/*RLock l = redissonClient.getLock("ssss");
		l.lock();*/
		RedissLockUtil.lock("test123");
		System.out.println("=====================");
		RBucket<String> keyObject = redissonClient.getBucket("aaa");
		String aa = keyObject.get();
		if (aa != null) {
			keyObject.delete();
		}
		keyObject.set("sdfsdf");
		RedissLockUtil.unlock("test123");
	}
}
