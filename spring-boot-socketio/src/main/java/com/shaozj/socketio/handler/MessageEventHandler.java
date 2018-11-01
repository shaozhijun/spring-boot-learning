package com.shaozj.socketio.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.shaozj.socketio.model.ChatMessage;
import com.shaozj.socketio.model.LoginRequest;

/**
 * 
 * <p>ClassName: MessageEventHandler</p>
 * <p>Description: ����ͻ��˴����¼�</p>
 * <p>Author: szj</p>
 * <p>Date: 2018��11��1��</p>
 */
@Component
public class MessageEventHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);

	/**
	 * 
	 * <p>Description: ��� connect�¼�,���ͻ��˷�������ʱ���� </p>
	 * @param client
	 */
	@OnConnect
	public void onConnect(SocketIOClient client) {
		if (client != null) {
//			String username = client.getHandshakeData().getSingleUrlParam("username");
//            String password = client.getHandshakeData().getSingleUrlParam("password");
//            String sessionId = client.getSessionId().toString();
//            logger.info("���ӳɹ�, username=" + username + ", password=" + password + ", sessionId=" + sessionId);
			
			
			//jwt��֤
			logger.info("���ӳɹ�");
		} else {
			logger.error("�ͻ���Ϊ��");
		}
	}
	
	/**
	 * 
	 * <p>Description: ���onDisconnect�¼����ͻ��˶Ͽ�����ʱ���ã�ˢ�¿ͻ�����Ϣ</p>
	 * @param client
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		logger.info("�ͻ��˶Ͽ�����, sessionId=" + client.getSessionId().toString());
        client.disconnect();
	}
	
	/**
	 * 
	 * <p>Description: ��Ϣ���</p>
	 * @param client
	 * @param ackRequest
	 * @param chat
	 */
	@OnEvent(value = "chatevent")
	public void onEvent(SocketIOClient client, AckRequest ackRequest, ChatMessage chat) {
		logger.info("���յ��ͻ�����Ϣ");
        if (ackRequest.isAckRequested()) {
            // send ack response with data to client
            ackRequest.sendAckData("�������ش�chatevent, userName=" + chat.getUserName() + ",message=" + chat.getMessage());
        }
	}
	
	@OnEvent(value = "login")
	public void onLogin(SocketIOClient client, AckRequest ackRequest, LoginRequest message) {
		logger.info("���յ��ͻ��˵�¼��Ϣ");
        if (ackRequest.isAckRequested()) {
            // send ack response with data to client
            ackRequest.sendAckData("�������ش�login", message.getCode(), message.getBody());
        }
	}
}
