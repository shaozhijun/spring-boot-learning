package com.shaozj.socketio.service;

public interface ISocketIOService {
	
	public void sendCount(String eventType, Integer count);
}
