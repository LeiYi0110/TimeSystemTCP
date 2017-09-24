package com.bjxc.supervise.netty.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageDecoder;
import com.bjxc.supervise.netty.MessageEncode;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * 作为客户端跟省平台通信
 * 在系统启动的时候建立连接，建立消息通道
 * 完成以下工作：1、省平台登录；2、省平台登出；3、转发消息给省平台；4、接收省平台的消息
 * 目前用一个消息通道，异步消息处理机制完成多个终端同时请求的情况
 * 
 * @author levin修改
 */
public class ProvinceClient implements Client{
	private static final Logger logger = LoggerFactory.getLogger(ProvinceClient.class);
	
	//省平台正式接口
	private int port = 8585;
	private String host = "114.55.41.128";
	
	//测试接口
//	private int port = 8383;
//	private String host = "114.55.89.156";
	private String platformNumb = "A0041";
	private String connectPassword = "12345678";
	private int connectCode = 12345678 ;
	
	private ChannelFuture future ;

	

	/**
	 * TCP连接到省平台
	 * @param host
	 * @param port
	 * @throws Exception
	 */
	public void connect(final String host, final int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstarp = new Bootstrap();
			bootstarp.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer(HexUtils.HexStringToBinary("7e"));
							channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							channel.pipeline().addLast(new MessageDecoder());
							channel.pipeline().addLast(new MessageEncode());
							channel.pipeline().addLast(new ProvinceLoginAuthHandler(platformNumb,connectCode,connectPassword));
						}
					});
			future = bootstarp.connect(host, port).sync();
			logger.info("TCP连接到省平台" + host + ":" + port);
		}catch(Throwable ex){
			logger.error("TCP连接到省平台失败!",ex);
		}  finally {
			//Do nothing.			
		}
	}

	/**
	 * 启动
	 * @throws Exception
	 */
	public void start() throws Exception {
		connect(host,port);
		long oldTime = System.currentTimeMillis();
		while(true){
			if(this.future == null){
				long currentTime = System.currentTimeMillis();
				if((currentTime - oldTime) > 5000){
					throw new NullPointerException("time out");
				}
				Thread.sleep(1000);
			}else{
				break;
			}
		}
		logger.info("Connect province tcp server success");
	}

	
	public void setPort(int port) {
		this.port = port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setConnectCode(int connectCode) {
		this.connectCode = connectCode;
	}

	public void setConnectPassword(String connectPassword) {
		this.connectPassword = connectPassword;
	}

	public void setPlatformNumb(String platformNumb) {
		this.platformNumb = platformNumb;
	}

	public void writeAndFlush(Message msg) {
		synchronized (this.future) {
			this.future.channel().writeAndFlush(msg);
		}
	}
	
	public boolean isActive(){
		synchronized (this.future) {
			return this.future.channel().isActive();
		}
	}
	
	public boolean isOpen(){
		synchronized (this.future) {
			return this.future.channel().isOpen();
		}
	}
	
	public void writeAndFlush(String msg){
		synchronized (this.future) {
			byte[] req = HexUtils.HexStringToBinary(msg);
			ByteBuf message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			this.future.channel().writeAndFlush(message);
		}
	}

}
