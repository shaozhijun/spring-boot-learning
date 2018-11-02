package com.shaozj.socketio.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_client_info")
public class ClientInfo {
	
	@Id
	private Long id;
	
	/**
	 * 是否连接
	 */
	private Short connected;
	
	private String clientid;
	
	private Long mostsignbits;
	
	private Long leastsignbits;
	
	private Date lastconnecteddate;

	public Long getMostsignbits() {
		return mostsignbits;
	}

	public void setMostsignbits(Long mostsignbits) {
		this.mostsignbits = mostsignbits;
	}

	public Long getLeastsignbits() {
		return leastsignbits;
	}

	public void setLeastsignbits(Long leastsignbits) {
		this.leastsignbits = leastsignbits;
	}

	public Date getLastconnecteddate() {
		return lastconnecteddate;
	}

	public void setLastconnecteddate(Date lastconnecteddate) {
		this.lastconnecteddate = lastconnecteddate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getConnected() {
		return connected;
	}

	public void setConnected(Short connected) {
		this.connected = connected;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
}
