package com.shaozj.activemq.enums;

public enum QueueEnum {
	
	QUEUE("mytest.queue"),
	
	TOPIC("mytest.topic");
	
	public String name;
	
	private QueueEnum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
