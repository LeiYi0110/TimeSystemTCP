package com.bjxc.supervise.netty.server;

import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务端的心跳
 * @author cras
 *
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message = (Message)msg;
		if(message.getHeader() != null && message.getHeader().getId() == 0x0002){
			//心跳
			Message heartMessage = MessageUtils.successMessage(message.getHeader().getId(), message.getHeader().getNumber());
			ctx.writeAndFlush(heartMessage);
		}else{
			ctx.fireChannelRead(msg);
		}
	}

}
