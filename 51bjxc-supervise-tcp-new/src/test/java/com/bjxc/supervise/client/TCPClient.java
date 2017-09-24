/*package com.bjxc.supervise.client;

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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageDecoder;
import com.bjxc.supervise.netty.MessageEncode;

@Service
public class TCPClient {
	private static final Logger logger = LoggerFactory.getLogger(TCPClient.class);
	
	
	private int port = 8585;
//	private String host = "112.74.129.7";
	private String host = "127.0.0.1";
	
	
	private int port = 8181;
	private String host = "114.55.89.156";
	

	private ChannelFuture future = null;
	
	private boolean isDecode = true;
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	public void connect(final String host, final int port) throws Exception {
		// 配置服务器端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstarp = new Bootstrap();
			bootstarp.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer(HexUtils.HexStringToBinary("7e"));
							channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							
							if (isDecode) {
								channel.pipeline().addLast(new MessageDecoder());
							}
//							channel.pipeline().addLast(new MessageEncode());
							
							channel.pipeline().addLast(new TCPClientHandler());
						}

					});
			logger.info(host + ":" + port);
			// 绑定端口 同步等 待
			future = bootstarp.connect(host, port).sync();

			// 等 待服务端监听端口关闭
			future.channel().closeFuture().sync();
		}catch(Throwable ex){
			logger.error("client start",ex);
		} finally {
			// 退出释放线程池
			// group.shutdownGracefully();
			// 所有资源释放完成之后，清空资源，再次发起重连操作
			executor.execute(new Runnable() {
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
						try {
							connect(host, port);// 发起重连操作
						} catch (Exception e) {
							logger.error("", e);
						}
					} catch (InterruptedException e) {
						logger.error("", e);
					}
				}
			});
		}
	}

	*//**
	 * 将消息发送给省平台服务端
	 * 
	 * @param msg
	 *//*
	public void writeAndFlush(Message msg) {
		System.out.println("messageLength------------"+msg.getBody().length);
		this.future.channel().writeAndFlush(msg);
	}
	
	public void writeAndFlush(String msg){
		byte[] req = HexUtils.HexStringToBinary(msg);
		ByteBuf message = Unpooled.buffer(req.length);
		message.writeBytes(req);
		this.future.channel().writeAndFlush(message);
	}
	

	public void start() throws Exception {
		connect(host,port);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	@Test
	public void run() throws Exception{
		TCPClient tcpClient = new TCPClient();
		tcpClient.start();
		//String msg ="7E800100003D000001582020779900003C002C0064373032303243563530303000000000000000000000000000003134353632333733353534363537383930313233343502D4C14137373939D1A77B7E";
		//tcpClient.writeAndFlush(msg);
		*//**
		int i = 0;
		while(i < 10000){
			System.out.println(i);
			String msg ="7E800100003D000001582020779900003C002C0064373032303243563530303000000000000000000000000000003134353632333733353534363537383930313233343502D4C14137373939D1A77B7E";
			tcpClient.writeAndFlush(msg);
			i++;
			Thread.sleep(1000);
		}
		**//*
	}
	
	
		 public static void main(String[] args){
			 byte[] b1,b2,b3,b4;//定义变量
			 b1=new byte[1024*1024];//分配 1MB 堆空间，考察堆空间的使用情况
			 b2=new byte[1024*1024];
			 b3=new byte[1024*1024];
			 b4=new byte[1024*1024];
			 }
}

*/