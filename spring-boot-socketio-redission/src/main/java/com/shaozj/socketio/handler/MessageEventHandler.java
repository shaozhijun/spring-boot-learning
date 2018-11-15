package com.shaozj.socketio.handler;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.shaozj.socketio.bean.MessageInfo;
import com.shaozj.socketio.model.SocketRoom;
import com.shaozj.socketio.repository.ISocketRoomRepository;

/**
 * 
 * <p>
 * ClassName: MessageEventHandler
 * </p>
 * <p>
 * Description: 处理客户端触发事件
 * 
 * 		集成Redission，Redission主要起到一个分发中心作用，用户通过socket.io namespace订阅房间号后
 * 		socket.io server则往redis订阅（subscribe）该房间号
 * 		当该房间某一个用户发送消息时，则通过redis的publish功能往该房间号推送用户发送信息
 * 		这样所有订阅房间号channel的websocket连接都会受到消息回调，然后推送给客户端
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

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageEventHandler.class);


	@Autowired
	SocketIOServer server;
	@Autowired
	ISocketRoomRepository socketRoomRepository;
	
	@OnConnect
	public void onConnect(SocketIOClient client) {
		String sourceId = client.getHandshakeData().getSingleUrlParam("sourceId");
		LOGGER.info("客户端连接成功: sourceId = {}", sourceId);
		if (StringUtils.isEmpty(sourceId)) {
			return ;
		}
		SocketRoom socketRoom = socketRoomRepository.findRoomBySourceid(sourceId);
		String roomId = UUID.randomUUID().toString();
		client.joinRoom(roomId);
		LOGGER.info("客户进入房间号: roomId = {}", roomId);
		if (socketRoom == null) {
			socketRoom = new SocketRoom();
			socketRoom.setCdate(new Date());
			socketRoom.setClientid(sourceId);
		}
		socketRoom.setRoomid(roomId);
		socketRoomRepository.save(socketRoom);
	}

	
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		String sourceId = client.getHandshakeData().getSingleUrlParam("sourceId");
		if (StringUtils.isEmpty(sourceId)) {
			return ;
		}
		LOGGER.info("客户端断开连接, sessionId=" + client.getSessionId().toString());
		SocketRoom socketRoom = socketRoomRepository.findRoomBySourceid(sourceId);
		if (socketRoom != null) {
			LOGGER.info("关闭房间，房间号: roomId = {}", socketRoom.getId());
			client.leaveRoom(socketRoom.getRoomid());
			socketRoomRepository.deleteById(socketRoom.getId());
		}
	}

	
	@OnEvent(value = "messageevent")
	public void onEvent(SocketIOClient client, AckRequest ackRequest, MessageInfo message) {
		
		LOGGER.info("客户发送信息: sourceId = {}, content = {}", message.getSourceId(),  message.getContent());
		String targetId = message.getTargetId();
		//查询目标用户房间号
		SocketRoom socketRoom = socketRoomRepository.findRoomBySourceid(targetId);
		if (socketRoom != null) {
			server.getRoomOperations(socketRoom.getRoomid()).sendEvent("messageevent", client, message);
		} else {
			LOGGER.info("目标用户不在线，targetId = {}", targetId);
		}
	}
}
