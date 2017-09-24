package com.bjxc.jtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.Action.Utils;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.LogoutCoach;
import com.bjxc.supervise.model.TransportObject;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.client.Client;
import com.bjxc.supervise.netty.server.TCPMap;

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
import io.netty.handler.timeout.IdleStateHandler;

public class JttClient{
	
	private static final Logger logger = LoggerFactory.getLogger(JttClient.class);
	
	private ChannelFuture future ;

	private static int jttPort = 3043;
	private static String jttHost = "59.32.177.26";

	private static final int readerIdleTimeSeconds = 60;
	private static final int writerIdleTimeSeconds = 60;
	private static final int allIdleTimeSeconds = 60;
	
	public void start() throws Exception {
		connect(jttHost,jttPort);
	}
	
	/**
	 * JTT连接到服务器
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
							channel.pipeline().addLast(new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds,allIdleTimeSeconds));
							channel.pipeline().addLast(new JttEncode());
							channel.pipeline().addLast(new JTTHandler());
						}
					});
			future = bootstarp.connect(host, port).sync();
			
			logger.info("JTT连接到服务器" + host + ":" + port);
		}catch(Throwable ex){
			logger.error("JTT连接到服务器失败!",ex);
		}  finally {
			//Do nothing.			
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

	public void writeAndFlush(JttMessage msg) {
		synchronized (this.future) {
			this.future.channel().writeAndFlush(msg);
		}
	}
	private Message getDefaultUpTransportMessage(TransportObject data,short transportId,String checkString ){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)1);
		extendMessageBody.setDeviceNo("1234560123456789");
		extendMessageBody.setData(data);
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);
		  
		Header header = new Header();
		header.setId((short)0x0900);
		header.setMobile("13012345343");
		header.setNumber((short)1);
		
		Message message = new Message();
		message.setHeader(header);
		message.setBody(upTransportMessageBody.getBytes());
		System.out.println("upTransportMessageBody.getBytes().length:"+upTransportMessageBody.getBytes().length);
		System.out.println("message.getBytes().length:"+message.getBytes().length);
		
		return message;
	}
}
