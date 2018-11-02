
## Netty-SocketIO 即时消息推送
### 1、背景

netty-socketio是基于netty的socket.io服务实现，可以无缝的对接前端使用的socketio-client.js。netty-socketio完整的实现了socket.io提供的监听前台事件、向指定客户端发送事件、将指定客户端加入指定房间事件、向指定房间广播事件、客户端从指定房间退出等 操作。




B/S架构下很多业务场景下我们需要服务端主动推送消息到客户端，在html5之前一般使用长轮询(除此之外还有iframe流或者Flash Socket)的方式来实现，而长轮询的方式缺点很明显，频繁交互的情况下，大量的连接被建立和释放，并且交互频率受限于两次http的请求间隔，html5开始可以使用websocket全双工的通信协议，在tomcat和jetty都有实现。

虽然在java1.4以后可以用nio包实现非阻塞的websocket通信,但是使用起来太过底层和复杂；netty封装了nio，使用了大量异步和事件驱动，封装了拆包粘包，实现了网络编程和业务分离，但即便如此，使用和优化netty还是需要深厚的网络编程知识，况且我们需要的仅仅是用户与用户之间的文本消息的推送,这里我们介绍一个基于netty实现的websocket服务端框架，socket.io屏蔽了使用netty的细节，针对聊天场景做了高度封装，并且提供了包含WEBSOCKET、POLLING等多种推送方式的支持,同时支持namespace(命名空间)、broadcast(广播)、room(房间/聊天室)、event(业务事件)、应用层ack机制。
