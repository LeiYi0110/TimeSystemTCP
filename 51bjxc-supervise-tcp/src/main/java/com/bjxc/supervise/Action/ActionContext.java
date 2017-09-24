package com.bjxc.supervise.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.bjxc.supervise.netty.Message;

import io.netty.channel.ChannelHandlerContext;

public class ActionContext {
	
	private ApplicationContext context;
	
	private Message message;
	
	private ChannelHandlerContext ctx;
	
	public ActionContext(ApplicationContext context,ChannelHandlerContext ctx,Message message){
		this.context = context;
		this.message = message;
		this.ctx = ctx;
	}
	
	public ApplicationContext getSpringAccplicationContext(){
		return context;
	}
	
	public Message getMessag(){
		return message;
	}
	
	public void writeAndFlush(Object msg){
		ctx.writeAndFlush(msg);
		ctx.flush();
	}
	
	public ChannelHandlerContext getChannelHandlerContext()
	{
		return this.ctx;
	}
	
	
	
	
}
