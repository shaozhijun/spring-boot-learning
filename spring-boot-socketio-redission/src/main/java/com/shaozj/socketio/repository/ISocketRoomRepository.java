package com.shaozj.socketio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shaozj.socketio.model.SocketRoom;

@Repository
public interface ISocketRoomRepository  extends CrudRepository<SocketRoom, Long>  {
	
	public SocketRoom findRoomBySourceid(String sourceId);
	
	public SocketRoom deleteRoomBySourceid(String sourceId);
}
