package com.shaozj.socketio.service;

public interface ISocketIOService {
	
	/**
	 * 
	 * <p>Description: ˢ�¿ͻ��ˣ����������Ϣ</p>
	 * @param eventType
	 * @param count
	 */
	public void sendCount(String eventType, Integer count);
}
