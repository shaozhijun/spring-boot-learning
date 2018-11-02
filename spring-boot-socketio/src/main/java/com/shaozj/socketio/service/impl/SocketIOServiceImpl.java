package com.shaozj.socketio.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.shaozj.socketio.service.ISocketIOService;


@Service
public class SocketIOServiceImpl implements ISocketIOService {

	@Autowired
	private SocketIOServer socketIOServer;
	
	@Override
	public void sendCount(String eventType, Integer count) {
		
		Collection<SocketIOClient> clients = socketIOServer.getAllClients();
        for(SocketIOClient client: clients){
            client.sendEvent(eventType, count);
        }
	}

}
