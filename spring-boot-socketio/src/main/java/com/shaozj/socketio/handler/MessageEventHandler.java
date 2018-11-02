package com.shaozj.socketio.handler;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * Description: ����ͻ��˴����¼�
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018��11��1��
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
	 * Description: ��� connect�¼�,���ͻ��˷�������ʱ���� �˴���clientid��sessionid�������ݿ���
	 * ������淢����Ϣʱ���ҵ���Ӧ��Ŀ��client
	 * </p>
	 * 
	 * @param client
	 */
	@OnConnect
	public void onConnect(SocketIOClient client) {
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
		if (clientInfo != null) {
			Date nowTime = new Date(System.currentTimeMillis());
			clientInfo.setConnected((short) 1);
			System.out.println(client.getSessionId().getLeastSignificantBits());
			// �������ش�UUID��128λ������64λֵ
			clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
			// �������ش�UUID��128λֵ����������64λ��
			clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
			clientInfo.setLastconnecteddate(nowTime);
			clientInfoRepository.save(clientInfo);
			// jwt��֤
			logger.info("���ӳɹ�");
		} else {
			logger.error("�ͻ���Ϊ��");
		}
	}

	/**
	 * 
	 * <p>
	 * Description: ���onDisconnect�¼����ͻ��˶Ͽ�����ʱ���ã�ˢ�¿ͻ�����Ϣ
	 * </p>
	 * 
	 * @param client
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		logger.info("�ͻ��˶Ͽ�����, sessionId=" + client.getSessionId().toString());
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
		if (clientInfo != null) {
			clientInfo.setConnected((short) 0);
			clientInfo.setMostsignbits(null);
			clientInfo.setLeastsignbits(null);
			clientInfoRepository.save(clientInfo);
		}
	}

	/**
	 * 
	 * <p>
	 * Description: ��Ϣ���
	 * 	�����յ���Ϣ�󣬲��ҷ���Ŀ��ͻ��ˣ�����Ŀͻ��˷�����Ϣ���Ҹ��Լ�������Ϣ
	 * </p>
	 * 
	 * @param client
	 * @param ackRequest
	 * @param chat
	 */
	@OnEvent(value = "messageevent")
	public void onEvent(SocketIOClient client, AckRequest ackRequest, MessageInfo message) {
		logger.info("���յ��ͻ�����Ϣ");
		String targetClientId = message.getTargetClientId();
		ClientInfo clientInfo = clientInfoRepository.findClientByclientid(targetClientId);
		if (clientInfo != null && clientInfo.getConnected() != 0) {
			UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
			System.out.println(uuid.toString());
			MessageInfo sendData = new MessageInfo();
			sendData.setSourceClientId(message.getSourceClientId());
			sendData.setTargetClientId(message.getTargetClientId());
			sendData.setMsgType("chat");
			sendData.setMsgContent(message.getMsgContent());
			client.sendEvent("messageevent", sendData);
			server.getClient(uuid).sendEvent("messageevent", sendData);
		}
		if (ackRequest.isAckRequested()) {
			// ackRequest.sendAckData("�������ش�chatevent, userName=" + chat.getUserName() +
			// ",message=" + chat.getMessage());
		}
	}
}
