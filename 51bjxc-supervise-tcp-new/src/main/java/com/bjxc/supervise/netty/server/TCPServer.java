package com.bjxc.supervise.netty.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.MessageDecoder;
import com.bjxc.supervise.netty.MessageEncode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 计时平台 TCP服务端
 * @author Administrator
 *
 */
@Service
public class TCPServer {
	private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);

	private ApplicationContext context; 
	
	private Map<String,Class> actionMap;

	private int port = 8585;

	public  String remoteip = "112.74.129.7";
//	public  String remoteip = "192.168.1.6";
//	public  String remoteip = "127.0.0.1";
	
	




	
	public void start() throws Exception{
		//配置服务器端NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap bootstarp = new ServerBootstrap();
			bootstarp.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel channel)
								throws Exception {
							//消息粘包
							ByteBuf delimiter = Unpooled.copiedBuffer(HexUtils.HexStringToBinary("7e"));
							channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
							
							channel.pipeline().addLast(new MessageDecoder());
							
							channel.pipeline().addLast(new MessageEncode());
							
							//消息拼接
							channel.pipeline().addLast(new MessagePrepender());
							//TODO:超过一小时无心跳或交互 则断开
							channel.pipeline().addLast(new ReadTimeoutHandler(60*60));
							//心跳回复
							channel.pipeline().addLast(new HeartBeatResponseHandler());
							//鉴权
							channel.pipeline().addLast(new LoginAuthRequestHandler());
							//消息权限保护
							channel.pipeline().addLast(new SecurityRequestHandler());
							channel.pipeline().addLast(new ServerDispatcher(context,actionMap));
							//消息透传
							channel.pipeline().addLast(new PassthroughHandler(context));
							
							
						}
					});
			//绑定端口 同步等 待
			bootstarp.bind(remoteip,port).sync();
			logger.info("Netty server start ok : " + (remoteip + " : " + port));
			
			//等 待服务端监听端口关闭
			//future.channel().closeFuture().sync();
		}catch(Throwable ex){
			logger.error("", ex);
		}
	}

	public static void main(String[] args) throws Exception {
		new TCPServer().start();
	}

	public void setPort(int port) {
		this.port = port;
	}


	public void setActionMap(Map<String, Class> actionMap) {
		this.actionMap = actionMap;
	}
	
	//手动调用注入
	public void setSpringApplicationContext( ApplicationContext context){
		this.context = context;
	}
	
}
