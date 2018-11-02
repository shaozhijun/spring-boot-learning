package com.shaozj.socketio.repository;

import org.springframework.data.repository.CrudRepository;

import com.shaozj.socketio.model.ClientInfo;

public interface ClientInfoRepository extends CrudRepository<ClientInfo, Long>  {
	
	ClientInfo findClientByclientid(String clientId);
}
