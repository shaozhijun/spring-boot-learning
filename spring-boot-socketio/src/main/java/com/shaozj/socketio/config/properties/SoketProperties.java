package com.shaozj.socketio.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SoketProperties.PREFIX)
public class SoketProperties {
	
	public static final String PREFIX = "shaozj";
	
	/**
	 * socket�˿�
	 */
	private Integer socketPort;
	
	/**
	 * Ping��Ϣ��������룩
	 */
	private Integer pingInterval;
	
	/**
	 * Ping��Ϣ��ʱʱ�䣨���룩
	 */
	private Integer pingTimeout;
	
	/**
	 * APK�ļ�����URLǰ׺
	 */
	private String apkUrlPrefix;

	public Integer getSocketPort() {
		return socketPort;
	}

	public void setSocketPort(Integer socketPort) {
		this.socketPort = socketPort;
	}

	public Integer getPingInterval() {
		return pingInterval;
	}

	public void setPingInterval(Integer pingInterval) {
		this.pingInterval = pingInterval;
	}

	public Integer getPingTimeout() {
		return pingTimeout;
	}

	public void setPingTimeout(Integer pingTimeout) {
		this.pingTimeout = pingTimeout;
	}

	public String getApkUrlPrefix() {
		return apkUrlPrefix;
	}

	public void setApkUrlPrefix(String apkUrlPrefix) {
		this.apkUrlPrefix = apkUrlPrefix;
	}
	
	
}
