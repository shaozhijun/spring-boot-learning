package com.shaozj.redisson.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
	
	private String password;
	
	private String masterName;
	
	private String address;
	
	private int database;
	
	private int timeout = 3000;
	
	private int connectionPoolSize = 64;
	
	private int connectionMinimumIdleSize=10;
	
	private int masterConnectionPoolSize = 250;
	
	private int slaveConnectionPoolSize = 250;
	
	private String[] sentinelAddresses;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}

	public int getConnectionMinimumIdleSize() {
		return connectionMinimumIdleSize;
	}

	public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
		this.connectionMinimumIdleSize = connectionMinimumIdleSize;
	}

	public int getMasterConnectionPoolSize() {
		return masterConnectionPoolSize;
	}

	public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
		this.masterConnectionPoolSize = masterConnectionPoolSize;
	}

	public int getSlaveConnectionPoolSize() {
		return slaveConnectionPoolSize;
	}

	public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
		this.slaveConnectionPoolSize = slaveConnectionPoolSize;
	}

	public String[] getSentinelAddresses() {
		return sentinelAddresses;
	}

	public void setSentinelAddresses(String[] sentinelAddresses) {
		this.sentinelAddresses = sentinelAddresses;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
}
