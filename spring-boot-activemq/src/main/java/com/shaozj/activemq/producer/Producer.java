package com.shaozj.activemq.producer;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
    private Queue queue;
    @Autowired
    private Topic topic;
    
    private static int count= 0;
    
    @Scheduled(fixedDelay=3000)
    public void sendQueue() {
    	jmsMessagingTemplate.convertAndSend(this.queue,"hi.activeMQ,index=");
    }
    
    @Scheduled(fixedDelay=3000)
    public void sendTopic() {
    	jmsMessagingTemplate.convertAndSend(this.topic, "hi,activeMQ( topic )，index=" + count++);
    }
}
