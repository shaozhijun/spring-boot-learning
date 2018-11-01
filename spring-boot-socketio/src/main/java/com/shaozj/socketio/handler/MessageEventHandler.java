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
 * <p>Description: 处理客户端触发事件</p>
 * <p>Author: szj</p>
 * <p>Date: 2018年11月1日</p>
 */
@Component
public class MessageEventHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);

	/**
	 * 
	 * <p>Description: 添加 connect事件,当客户端发起链接时调用 </p>
	 * @param client
	 */
	@OnConnect
	public void onConnect(SocketIOClient client) {
		if (client != null) {
//			String username = client.getHandshakeData().getSingleUrlParam("username");
//            String password = client.getHandshakeData().getSingleUrlParam("password");
//            String sessionId = client.getSessionId().toString();
//            logger.info("连接成功, username=" + username + ", password=" + password + ", sessionId=" + sessionId);
			
			
			//jwt验证
			logger.info("链接成功");
		} else {
			logger.error("客户端为空");
		}
	}
	
	/**
	 * 
	 * <p>Description: 添加onDisconnect事件，客户端断开链接时调用，刷新客户端信息</p>
	 * @param client
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		logger.info("客户端断开连接, sessionId=" + client.getSessionId().toString());
        client.disconnect();
	}
	
	/**
	 * 
	 * <p>Description: 消息入口</p>
	 * @param client
	 * @param ackRequest
	 * @param chat
	 */
	@OnEvent(value = "chatevent")
	public void onEvent(SocketIOClient client, AckRequest ackRequest, ChatMessage chat) {
		logger.info("接收到客户端消息");
        if (ackRequest.isAckRequested()) {
            // send ack response with data to client
            ackRequest.sendAckData("服务器回答chatevent, userName=" + chat.getUserName() + ",message=" + chat.getMessage());
        }
	}
	
	@OnEvent(value = "login")
	public void onLogin(SocketIOClient client, AckRequest ackRequest, LoginRequest message) {
		logger.info("接收到客户端登录消息");
        if (ackRequest.isAckRequested()) {
            // send ack response with data to client
            ackRequest.sendAckData("服务器回答login", message.getCode(), message.getBody());
        }
	}
}
