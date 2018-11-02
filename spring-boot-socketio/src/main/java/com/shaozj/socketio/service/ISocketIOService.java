package com.shaozj.socketio.service;

public interface ISocketIOService {
	
	/**
	 * 
	 * <p>Description: 刷新客户端（浏览器）信息</p>
	 * @param eventType
	 * @param count
	 */
	public void sendCount(String eventType, Integer count);
}
