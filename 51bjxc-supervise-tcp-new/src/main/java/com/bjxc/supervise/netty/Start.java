package com.bjxc.supervise.netty;

import org.springframework.context.ApplicationContext;

import com.bjxc.supervise.netty.server.SpringApplicationContext;
import com.bjxc.supervise.netty.server.TCPServer;


/**
 * TCP Server启动类
 * @author levin修改
 *
 */
public class Start {
	public static void main(String[] args) throws Exception {
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/app*.xml");
		ApplicationContext context = SpringApplicationContext.getInstance().getContext();
		TCPServer tcpServer = context.getBean(com.bjxc.supervise.netty.server.TCPServer.class);
		tcpServer.setSpringApplicationContext(context);
		tcpServer.start();
	}
}
