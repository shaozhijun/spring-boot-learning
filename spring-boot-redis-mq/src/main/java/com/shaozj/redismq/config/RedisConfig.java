package com.shaozj.redismq.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.shaozj.redismq.receiver.Receiver;

/**
 * 配置Redis
 * @author shaozj
 *
 */
@Configuration
public class RedisConfig {

	@Bean
	public Receiver receiver(CountDownLatch latch) {
		return new Receiver(latch);
	}
	/**
	 * redis消息队列连接工厂
	 * @return
	 */
    @Bean
    public CountDownLatch latch() {
    	return new CountDownLatch(1);
    }
	
	/**
	 * 注入消息监听容器（监听Redis服务器中channel通道的信息写入）
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @return
	 */
	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("message"));//监听redis中的channel通道
		return container;
	}
	
	
	/**
	 * 消息监听适配器
	 * 把MessageReceiver注册为一个消息监听器，并指定消息接收的方法（receiveMessage）
	 * 如果不指定消息接收的方法，消息监听器会默认的寻找MessageReceiver中的handleMessage方法作为接收方法
	 * @return
	 */
	@Bean
	public MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	/**
	 * 注入Redis模板
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
	
}
