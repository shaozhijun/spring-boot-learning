package com.shaozj.socketio.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = RedisProperties.PREFIX)
public class RedisProperties {

	public static final String PREFIX = "spring.redis";
	
	public String host;
	
	public Integer port;
	
	public String password;
	
	public Integer database;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDatabase() {
		return database;
	}

	public void setDatabase(Integer database) {
		this.database = database;
	}
}
