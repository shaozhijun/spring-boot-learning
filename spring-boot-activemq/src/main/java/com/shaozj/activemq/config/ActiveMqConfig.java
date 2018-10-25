package com.shaozj.activemq.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import com.shaozj.activemq.enums.QueueEnum;

/**
 * 系统默认采用queue模式
 * @author szj
 *
 */
@Configuration
@EnableJms
public class ActiveMqConfig {
	
	/**
	 * 注入Queue对象
	 * @return
	 */
	@Bean
	public Queue queue() {
		return new ActiveMQQueue(QueueEnum.QUEUE.name);  
	}
	
	/**
	 * 注入 Topic对象
	 * @return
	 */
	@Bean
	public Topic topic() {
		return new ActiveMQTopic(QueueEnum.TOPIC.name);
	}
	
	/**
	 *  队列模式
	 * @param connectionFactory
	 * @return
	 */
	@Bean("jmsListenerContainerQueue")
	public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory connectionFactory ) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
        //设置连接数
        factory.setConcurrency("3-10");
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setPubSubDomain(false);
        return factory;
	}
	
	
	/**
	 * 普通订阅模式
	 * 发送消息不会存储到磁盘中，broker宕机重启后，消息丢失
	 * @param connectionFactory
	 * @return
	 */
	@Bean("jmsListenerContainerTopic")
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setRecoveryInterval(1000L);//重连间隔时间
        factory.setPubSubDomain(true);
        return factory;
    }
	
	
	/**
	 * 持久订阅模式
	 *  传输消息会保存到磁盘中，即“存储转发”方式，先把消息存储到磁盘中，然后再将消息转发给订阅者
	 *  当Broker宕机恢复后，消息还在。
	 *  当生产者消息传递给broker后，broker将该消息存储到磁盘中，在Broker将消息发送给Subscriber之前，Broker宕机了
	 *  如果采用持久传输Broker重启后，从磁盘独处消息再传递给Subscriber，如果非持久传输，该消息就丢失了。
	 * @param connectionFactory
	 * @return
	 */
	@Bean("jmsListenerContainerDurableTopic")
    public JmsListenerContainerFactory<?> jmsListenerContainerDurableTopic(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setPubSubDomain(true);
        
        // 给订阅者一个名字,并开启持久订阅
        factory.setClientId("client_id");
        factory.setSubscriptionDurable(true);
        return factory;
    } 
	
}
