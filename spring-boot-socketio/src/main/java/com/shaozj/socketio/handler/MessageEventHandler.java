package com.shaozj.socketio.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.shaozj.socketio.bean.MessageInfo;
import com.shaozj.socketio.model.ClientInfo;
import com.shaozj.socketio.repository.ClientInfoRepository;

/**
 * 
 * <p>
 * ClassName: MessageEventHandler
 * </p>
 * <p>
 * Description: 处理客户端触发事件
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018年11月1日
 * </p>
 */
@Component
public class MessageEventHandler {

	private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);

	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	SocketIOServer server;
	/**
	 * 
	 * <p>
	 * Description: 添加 connect事件,当客户端发起链接时调用 此处将clientid与sessionid存入数据库中
	 * 方便后面发送消息时查找到对应的目标client
	 * </p>
	 * 
	 * @param client
	 */
	@OnConnect
	public void onConnect(SocketIOClient client) {
		
		System.out.println(JSON.toJSONString(client));
		client.joinRoom("123");
		System.out.println(JSON.toJSONString(client));

		
		
		
		
		
		
		//		client.set("sessionId", client.getSessionId().toString());
		/*String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
		if (clientInfo != null) {
			Date nowTime = new Date(System.currentTimeMillis());
			clientInfo.setConnected((short) 1);
			System.out.println(client.getSessionId().getLeastSignificantBits());
			// 用来返回此UUID的128位最显著64位值
			clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
			// 用来返回此UUID的128位值的至少显著64位。
			clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
			clientInfo.setLastconnecteddate(nowTime);
			clientInfoRepository.save(clientInfo);
			
			// jwt验证
			logger.info("链接成功");
		} else {
			System.out.println(client.getSessionId());
			logger.error("客户端为空");
		}*/
	}

	/**
	 * 
	 * <p>
	 * Description: 添加onDisconnect事件，客户端断开链接时调用，刷新客户端信息
	 * </p>
	 * 
	 * @param client
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		System.out.println(JSON.toJSONString(client));
		logger.info("客户端断开连接, sessionId=" + client.getSessionId().toString());
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
		if (clientInfo != null) {
			clientInfo.setConnected((short) 0);
			clientInfo.setMostsignbits(null);
			clientInfo.setLeastsignbits(null);
			clientInfoRepository.save(clientInfo);
		}
		client.leaveRoom("123");
//		client.del("sessionId");
	}

	/**
	 * 
	 * <p>
	 * Description: 消息入口
	 * 	当接收到消息后，查找发送目标客户端，并想改客户端发送信息，且给自己发送信息
	 * </p>
	 * 
	 * @param client
	 * @param ackRequest
	 * @param chat
	 */
	@OnEvent(value = "messageevent")
	public void onEvent(SocketIOClient client, AckRequest ackRequest, MessageInfo message) {
		logger.info("接收到客户端消息");
		/*String targetClientId = message.getTargetClientId();
		ClientInfo clientInfo = clientInfoRepository.findClientByclientid(targetClientId);
		if (clientInfo != null && clientInfo.getConnected() != 0) {
			UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
			MessageInfo sendData = new MessageInfo();
			sendData.setSourceClientId(message.getSourceClientId());
			sendData.setTargetClientId(message.getTargetClientId());
			sendData.setMsgType("chat");
			sendData.setMsgContent(message.getMsgContent());
			client.sendEvent("messageevent", sendData);
//			server.getClient(uuid).sendEvent("messageevent", sendData);
			
		}
		if (ackRequest.isAckRequested()) {
			
			// ackRequest.sendAckData("服务器回答chatevent, userName=" + chat.getUserName() +
			// ",message=" + chat.getMessage());
		}*/
		MessageInfo sendData = new MessageInfo();
		sendData.setMsgType("chat");
		sendData.setSourceClientId(message.getSourceClientId());
		sendData.setTargetClientId(message.getTargetClientId());
		sendData.setMsgContent(message.getMsgContent());
		server.getRoomOperations("123").sendEvent("messageevent", sendData);
	}
}
