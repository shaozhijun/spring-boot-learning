package com.shaozj.socketio.bean;

public class MessageInfo {
	
	/**
	 * Դ�ͻ���id
	 */
	private String sourceClientId;
	
	/**
	 * Ŀ��ͻ���id
	 */
	private String targetClientId;
	
	/**
	 * ��Ϣ����
	 */
	private String msgType;
	
	/**
	 * ��Ϣ����
	 */
	private String msgContent;

	public String getSourceClientId() {
		return sourceClientId;
	}

	public void setSourceClientId(String sourceClientId) {
		this.sourceClientId = sourceClientId;
	}

	public String getTargetClientId() {
		return targetClientId;
	}

	public void setTargetClientId(String targetClientId) {
		this.targetClientId = targetClientId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
}
