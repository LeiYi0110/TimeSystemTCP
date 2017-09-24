package com.bjxc.supervise.netty.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 缁堢閴存潈淇濇姢
 * 
 * @author cras
 *
 */
public class SecurityRequestHandler extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginAuthRequestHandler.class);
	private static List<String> securityMessagelist = new ArrayList<String>();
	//不需要鉴权的消息列表
	static{
		//登录
		securityMessagelist.add("01F0");
		//登出
		securityMessagelist.add("01F1");
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message)msg;
		try{
			String msgId = Integer.toHexString(message.getHeader().getId());
			if(securityMessagelist.contains(msgId.toUpperCase())){
				if(message.getHeader().isSecurity()){
					//鉴权通过
				}else{
					logger.warn("not pass auth!");
					Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
					ctx.writeAndFlush(result);
					return;
				}
			}
			ctx.fireChannelRead(msg);
	
		}catch(Throwable ex){
			
			logger.error("message security hander" + Integer.toHexString(message.getHeader().getId()), ex);
			Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
			ctx.writeAndFlush(result);
		}
	}

}
