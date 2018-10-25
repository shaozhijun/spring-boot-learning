package com.shaozj.test;

import javax.jms.Queue;
import javax.jms.Topic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

public class ActiveMqTest extends BaseTest {

	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
    private Queue queue;
    @Autowired
    private Topic topic;
    
    private static int count= 0;
    
    @Test
    public void sendQueue() {
    	jmsMessagingTemplate.convertAndSend(this.queue,"hi.activeMQ,index=");
    }
    
    @Test
    public void sendTopic() {
    	jmsMessagingTemplate.convertAndSend(this.topic, "hi,activeMQ( topic )，index=" + count++);
    }
}
