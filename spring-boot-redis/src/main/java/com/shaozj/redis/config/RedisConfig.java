package com.shaozj.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.shaozj.redis.config.properties.RedisProperties;
import com.shaozj.redis.serializer.FastJsonRedisSerializer;
import com.shaozj.redis.utils.RedisClient;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 初始化Redis配置
 * @author shaozj
 *
 */
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfig {

	@Autowired
	RedisProperties redisProperties;
	
	/**
	 * 注入 fastJson序列化
	 * @return
	 */
	@Bean
	public RedisSerializer<Object> fastJsonRedisSerializer() {
        return new FastJsonRedisSerializer<Object>(Object.class);
    }
	
	/**
	 * 注入 redisTemplate,设置fastJson序列化
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer<Object> fastJsonRedisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		//开启事务
		redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(connectionFactory);
        //配置key和value序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
		return redisTemplate;
	}

	/**
	 * 配置 redis连接池
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//最大空闲数
		poolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
		//连接池的最大数据库连接数
		poolConfig.setMaxTotal(redisProperties.getPool().getMaxTotal());
		//最大建立链接等待时间
		poolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWaitMillis());
		//逐出链接的最小空闲时间，默认1800000毫秒(30分钟)
		poolConfig.setMinEvictableIdleTimeMillis(redisProperties.getPool().getMinEvictableIdleTimeMillis());
		//每次逐出检查时，逐出的最大数目，如果为负数就是1/abs(n), 默认3
		poolConfig.setNumTestsPerEvictionRun(redisProperties.getPool().getNumTestsPerEvictionRun());
		//逐出扫描的时间间隔（毫秒），如果为负数，则不运行逐出线程，默认-1
		poolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getPool().getTimeBetweenEvictionRunsMillis());
		//是否在从池中去除连接前进行检验，如果检验失败，则从池中去除链接并尝试取另一个
		poolConfig.setTestOnBorrow(redisProperties.getPool().isTestOnBorrow());
		//在空闲时检查有效性，默认false
		poolConfig.setTestWhileIdle(redisProperties.getPool().isTestWhileIdle());
		return poolConfig;
	}
	
	/**
	 * 注入 JedisConnectionFactory
	 * @return
	 */
	@Bean
	public JedisConnectionFactory connectionFactory(JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig);
		connectionFactory.setPoolConfig(jedisPoolConfig);  
		connectionFactory.setHostName(redisProperties.getHost()); 
		connectionFactory.setPort(redisProperties.getPort()); 
		connectionFactory.setTimeout(redisProperties.getTimeout()); 
		return connectionFactory;
	} 
	
	/**
	 * 注入 RedisClient
	 * @return
	 */
	@Bean
	public RedisClient redisClient(RedisTemplate<String, Object> redisTemplate) {
		RedisClient client = new RedisClient();
		client.setRedisTemplate(redisTemplate);
		return client;
	}
}
