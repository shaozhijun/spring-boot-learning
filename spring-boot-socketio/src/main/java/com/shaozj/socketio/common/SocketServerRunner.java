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
 * Description: ����socket����
 * ��������е�����⣬ÿ������springboot��Ŀ��ʱ����������ܼ�ʱ�رգ��ر�spring-boot��Ŀweb�����
 * �ȼ����������ɣ�������������ᱨsocket�˿ڱ�ռ���쳣
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018��11��1��
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
		logger.info("ServerRunner ��ʼ������...");
		server.start();
	}
}
