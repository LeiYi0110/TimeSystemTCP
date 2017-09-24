package com.bjxc.supervise.netty.client;

import com.bjxc.supervise.netty.Message;

public interface Client {
	 boolean isOpen();
	 void writeAndFlush(String msg);
	 boolean isActive();
	 void writeAndFlush(Message msg) ;
	
}
