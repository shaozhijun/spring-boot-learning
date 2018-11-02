package com.shaozj.socketio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.shaozj.socketio.config.properties.SoketProperties;

@Configuration
@EnableConfigurationProperties({ SoketProperties.class })
public class SocketConfig {

	@Autowired
	private SoketProperties soketProperties;

	@Bean
	public SocketIOServer socketIOServer() {
		
		//����Socket����
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		// ���ü����˿�
		config.setPort(soketProperties.getSocketPort());
		// Э��������ʱʱ�䣨���룩��Ĭ��10000��HTTP��������ΪwsЭ��ʱ��
		config.setUpgradeTimeout(10000);
		// Ping��Ϣ���ʱ�䣨���룩��Ĭ��25000���ͻ��������������һ��������Ϣʱ����
		config.setPingInterval(soketProperties.getPingInterval());
		// Ping��Ϣ��ʱʱ�䣨���룩��Ĭ��60000�����ʱ������û���յ�������Ϣ�ͻᷢ�ͳ�ʱ�¼�
		config.setPingTimeout(soketProperties.getPingTimeout());
		
		// ����Э�����ʹ��JWT��token����
		// �ó������������֤
		config.setAuthorizationListener(new AuthorizationListener() {
			@Override
			public boolean isAuthorized(HandshakeData data) {
				//http://localhost:8081?token=xxxxxxxxxxxxx
				data.getSingleUrlParam("token"); 
				return true;
			}
		});	
		return new SocketIOServer(config);
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner() {
		return new SpringAnnotationScanner(socketIOServer());
	}
}
