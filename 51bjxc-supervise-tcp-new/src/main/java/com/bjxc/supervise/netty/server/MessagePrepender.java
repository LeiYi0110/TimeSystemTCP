package com.bjxc.supervise.netty.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.Messages;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 濞戝牊浼呴幏鍏煎复
 * @author xueyingou
 *
 */
public class MessagePrepender  extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MessagePrepender.class);
	//闂囷拷鐟曚胶绮嶇痪璺ㄢ柤閸樿崵娣幎銈忕礉濞撳懘娅庢潻鍥ㄦ埂閻ㄥ嫭绉烽幁锟�
	private Map<String,Messages> msgMap = new HashMap<String,Messages>();
	private short previousParam = -1;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 Message message = (Message)msg;
		 
		try{
			 if(message.getHeader().isSubPackage() && message.getHeader().getPackageSize() > 0){
				 logger.info("msg id: " + message.getHeader().getId());
				 logger.info("msg package size: " + message.getHeader().getPackageSize());
				 logger.info("msg package index: " + message.getHeader().getPackageIndex());
				 //闂囷拷鐟曚胶绮嶉崠锟�
				 String msgKey = buildMessageKey(message);
				 logger.info("msg msgKey: " + msgKey);
				Messages  messages = msgMap.get(msgKey);
				if(messages == null){
					System.out.println("---------------messages涓虹┖---------------"+msgKey);
					messages = new Messages(message.getHeader().getPackageSize(),ctx);
					msgMap.put(msgKey, messages);
				}
				//鐏忓棙绉烽幁顖氬閸忋儱绨遍柌锟�
				messages.addMessage(message.getHeader().getPackageIndex(), message);
				
				//濞戝牊浼呭鎻掔暚閹存劖瀚剧紒锟�
				if(messages.complete()){
					logger.info("msg szie: " + message.getHeader().getLength());
					message.setBody(messages.toBody());
					logger.info("body[] szie: " + message.getBody().length);
					//濞戝牊浼呭锟芥稉瀣╃炊
					ctx.fireChannelRead(msg);
					//娴犲孩绉烽幁顖氱氨闁插瞼些闂勶拷
					msgMap.remove(msgKey);
					previousParam = -1;
				}
				
				//返回给终端通用消息
				Message result = MessageUtils.successMessage(message.getHeader().getId(), message.getHeader().getNumber());
				ctx.writeAndFlush(result);
				
				//原封转发消息到省平台
				ChannelHandlerContext provinceContext = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_PLATFORMNO);
				provinceContext.writeAndFlush(message);
				logger.info("转发分包消息到省平台......"+ Integer.toHexString(message.getHeader().getId()));
				
				 return;
			 }
			 ctx.fireChannelRead(msg);
		}catch(Throwable ex){
			
			logger.error("message append hander" + Integer.toHexString(message.getHeader().getId()), ex);
			Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
			ctx.writeAndFlush(result);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	private String buildMessageKey(Message message) {
		short number = message.getHeader().getNumber();
		short packageIndex = message.getHeader().getPackageIndex();
		short firstNum = (short) (number-packageIndex+1);
		String msgKey = message.getHeader().getMobile()+"-"+message.getHeader().getId() + "-" + firstNum;
		return msgKey;
	}
}
