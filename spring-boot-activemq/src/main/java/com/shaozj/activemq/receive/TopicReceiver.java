package com.shaozj.activemq.receive;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {

	@JmsListener(destination = "mytest.topic",containerFactory="jmsListenerContainerTopic")
	public void receiveTopic1 (String message) {
		System.out.println("Topic Consumer1:" + message);
	}
	
	@JmsListener(destination = "mytest.topic", containerFactory="jmsListenerContainerTopic")
	public void receiveTopic2 (String message) {
		System.out.println("Topic Consumer2:" + message);
	}
}
