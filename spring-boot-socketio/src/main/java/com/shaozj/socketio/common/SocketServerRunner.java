package com.shaozj.socketio.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

/**
 * 
 * <p>
 * ClassName: SocketServerRunner
 * </p>
 * <p>
 * Description: 启动socket服务
 * 这个服务有点饿问题，每次启动springboot项目的时候，这个服务不能及时关闭，关闭spring-boot项目web服务后
 * 等几秒启动即可，如果立即启动会报socket端口被占用异常
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018年11月1日
 * </p>
 */
@Component
@Order(1)
public class SocketServerRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerRunner.class);

	@Autowired
	private SocketIOServer server;

	@Override
	public void run(String... args) {
		logger.info("ServerRunner 开始启动啦...");
		server.start();
	}
}
