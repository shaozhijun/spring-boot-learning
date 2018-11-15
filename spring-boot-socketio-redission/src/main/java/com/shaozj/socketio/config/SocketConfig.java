package com.shaozj.socketio.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.shaozj.socketio.config.properties.RedisProperties;
import com.shaozj.socketio.config.properties.SoketProperties;

@Configuration
@EnableConfigurationProperties({ SoketProperties.class, RedisProperties.class })
public class SocketConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketConfig.class);

	private static final String NAMESPACE = "even-websocket";
	
	@Autowired
	private SoketProperties soketProperties;
	@Autowired
	private RedisProperties redisProperties;

	@Bean
	public SocketIOServer socketIOServer() {
		// 创建Socket配置
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		// 设置监听端口
		config.setPort(soketProperties.getSocketPort());
		// 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议时间
		config.setUpgradeTimeout(10000);
		// Ping消息间隔时间（毫秒），默认25000。客户端向服务器发送一条心跳消息时间间隔
		config.setPingInterval(soketProperties.getPingInterval());
		// Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有收到心跳消息就会发送超时事件
		config.setPingTimeout(soketProperties.getPingTimeout());

		RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory(getRedissonClient());
		config.setStoreFactory(redisStoreFactory);

		// 握手协议参数使用JWT的token方案
		// 该出用来做身份验证
		config.setAuthorizationListener(new AuthorizationListener() {
			@Override
			public boolean isAuthorized(HandshakeData data) {
				// http://localhost:8081?token=xxxxxxxxxxxxx
				data.getSingleUrlParam("token");
				return true;
			}
		});
		final SocketIOServer server = new SocketIOServer(config);
		server.addNamespace(NAMESPACE);
		return server;
	}

	public RedissonClient getRedissonClient()  {
		LOGGER.info("creatting redisson client on redissonAddress=" + redisProperties.getHost());
		Config config = new Config();
		config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
				.setPassword(redisProperties.getPassword()).setDatabase(1);
		RedissonClient redisson = Redisson.create(config);
		LOGGER.info("redisson client connected");
		return redisson;
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner() {
		return new SpringAnnotationScanner(socketIOServer());
	}
}
