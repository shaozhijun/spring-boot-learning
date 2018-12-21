package com.shaozj.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.shaozj.redisson.config.properties.RedissonProperties;
import com.shaozj.redisson.locker.DistributedLocker;
import com.shaozj.redisson.locker.impl.RedissonDistributedLocker;
import com.shaozj.redisson.utils.RedissLockUtil;


@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfig {
	
	@Autowired
	RedissonProperties redissonProperties;
	
	/**
	 * 
	 * <p>Description: 哨兵模式自动匹配</p>
	 * @return
	 */
	@Bean
    @ConditionalOnProperty(name="redisson.master-name")
	public RedissonClient redissonSentinel() {
		Config config = new Config();
		SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setTimeout(redissonProperties.getTimeout())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());
        
        if(!StringUtils.isEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
	}
	
	
	@Bean
    @ConditionalOnProperty(name="redisson.address")
	RedissonClient redissonSingle() {
		Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize())
                .setDatabase(redissonProperties.getDatabase());
        
        if(!StringUtils.isEmpty(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
	}

	 /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
    	RedissonDistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        RedissLockUtil.setLocker(locker);
        return locker;
    }

}
