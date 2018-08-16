package com.shaozj.redismq.receiver;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消息接收者，接收到消息自动调用receiveMessage方法
 * @author shaozj
 *
 */
public class Receiver {
	
	private CountDownLatch latch;
	 
    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }
	
	/**
	 * 接收消息
	 * @param message
	 */
    public void receiveMessage(String message){ 
        System.out.println("--------------------" + message);  
        latch.countDown();
    }

}
