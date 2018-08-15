package com.shaozj.activemq.receive;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author szj
 *
 */
@Component
public class QueueReceiver {
	
	@JmsListener(destination = "mytest.queue", containerFactory="jmsListenerContainerQueue")
	public void receiveQueue1 (String message) {
		System.out.println("Queue Consumer1:" + message);
	}
	
	/**
	 * 监听  队列名为“mytest.queue” 的队列
	 * @param message
	 */
	@JmsListener(destination = "mytest.queue", containerFactory="jmsListenerContainerQueue")
	public void receiveQueue2 (String message) {
		System.out.println("Queue Consumer2:" + message);
	}
}
