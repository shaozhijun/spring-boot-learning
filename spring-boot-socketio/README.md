## netty-socketio即时消息推送

- ### 背景
    netty-socketio是基于netty的socket.io服务实现，可以无缝的对接前端使用的socketio-client.js。netty-socketio完整的实现了socket.io提供的监听前台事件、向指定客户端发送事件、将指定客户端加入指定房间事件、向指定房间广播事件、客户端从指定房间退出等 操作。

- ### 实现

> 引入pom依赖

```
<dependency>
	<groupId>com.corundumstudio.socketio</groupId>
	<artifactId>netty-socketio</artifactId>
	<version>1.7.13</version>
</dependency>
<dependency>
	<groupId>io.socket</groupId>
	<artifactId>socket.io-client</artifactId>
	<version>0.8.3</version>
</dependency>
```
> 客户端引入 socket.io.js

```
<script type="text/javascript" src="/js/socket.io.js"></script>
```

2) 创建SocketIOServer服务

```
@Bean
	public SocketIOServer socketIOServer() {
		
		//创建Socket配置
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		// 设置监听端口
		config.setPort(soketProperties.getSocketPort());
		// 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议时间
		config.setUpgradeTimeout(10000);
		// Ping消息间隔时间（毫秒），默认25000。客户端向服务器发送一条心跳消息时间间隔
		config.setPingInterval(soketProperties.getPingInterval());
		// Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有收到心跳消息就会发送超时事件
		config.setPingTimeout(soketProperties.getPingTimeout());
		
		// 握手协议参数使用JWT的token方案
		// 该出用来做身份验证
		config.setAuthorizationListener(new AuthorizationListener() {
			@Override
			public boolean isAuthorized(HandshakeData data) {
				//http://localhost:8081?token=xxxxxxxxxxxxx
				data.getSingleUrlParam("token"); 
				return true;
			}
		});	
		return new SocketIOServer(config);
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner() {
		return new SpringAnnotationScanner(socketIOServer());
	}
```

> 客户端

```
   var socket = io.connect('http://127.0.0.1:9099?clientid=' + clientid);
```

3) 添加 connect事件,当客户端发起链接时调用。

> 服务端

```
    @OnConnect
	public void onConnect(SocketIOClient client) {
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		if (clientId != null) {
			// jwt验证
			logger.info("链接成功");
		} else {
			logger.error("客户端为空");
		}
	}
```
> 客户端

```
socket.on('connect',function() {
                output('<span class="connect-msg">Client has connected to the server!</span>');
            });
```


4) 添加onDisconnect事件，客户端断开链接时调用，刷新客户端信息

> 服务端

```
@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		logger.info("客户端断开连接, sessionId=" + client.getSessionId().toString());
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		if (clientId != null) {
			
		}
	}
```
> 客户端

```
 socket.on('disconnect',
            function() {
                output('<span class="disconnect-msg">The client has disconnected!</span>');
            }); 
```


5) 消息入口，当接收到消息后，查找发送目标客户端，并想改客户端发送信息，且给自己发送信息

> 服务端

```
@OnEvent(value = "messageevent")
	public void onEvent(SocketIOClient client, AckRequest ackRequest, MessageInfo message) {
		logger.info("接收到客户端消息");
		String targetClientId = message.getTargetClientId();
		if (targetClientId != null) {
			
		}
		if (ackRequest.isAckRequested()) {
			// ackRequest.sendAckData("服务器回答chatevent, userName=" + chat.getUserName() +
			// ",message=" + chat.getMessage());
		}
	}

```

> 客户端

```
socket.on('messageevent',
            function(data) {
                output('<span class="username-msg">' + data.sourceClientId + ':</span> ' + data.msgContent);
            });
```


6) 后台向客户端发送及时消息

```
@Override
	public void sendCount(String eventType, Integer count) {
		Collection<SocketIOClient> clients = socketIOServer.getAllClients();
        for(SocketIOClient client: clients){
            client.sendEvent(eventType, count);
        }
	}
```

7) 启动服务

```
@Component
@Order(1)
public class SocketServerRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerRunner.class);

	@Autowired
	private SocketIOServer server;

	@Override
	public void run(String... args) {
		logger.info("ServerRunner 开始启动啦...");
		server.start();
	}
}
```



