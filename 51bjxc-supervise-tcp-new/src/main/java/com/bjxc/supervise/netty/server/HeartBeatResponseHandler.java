package com.bjxc.supervise.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务端的心跳
 * @author cras
 *
 */
public class HeartBeatResponseHandler extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(HeartBeatResponseHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message = (Message)msg;
		logger.info("HeartBeatResponse: id="+message.getHeader().getId());
		try{
			if(message.getHeader() != null && message.getHeader().getId() == 2){
				//心跳
				Message heartMessage = MessageUtils.successMessage(message.getHeader().getId(), message.getHeader().getNumber());
				ctx.writeAndFlush(heartMessage);
				logger.info("heart beat request: "+message.getHeader().getNumber());
			}else{
				ctx.fireChannelRead(msg);
			}
		}catch(Throwable ex){
			logger.error("heart beat hander" + Integer.toHexString(message.getHeader().getId()), ex);
			Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
			ctx.writeAndFlush(result);
		}
	}

}
