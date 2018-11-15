package com.shaozj.socketio.bean;

public class MessageInfo {
	
	/**
	 * 来源客户端ID
	 */
	private String sourceId;
	
	/**
	 *  目标客户端ID
	 */
	private String targetId;
	
	/**
	 * 消息内容
	 */
	private String content;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
