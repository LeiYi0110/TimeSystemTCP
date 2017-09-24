package com.bjxc.supervise.netty.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bjxc.supervise.Action.ActionContext;
import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LoggingHandler;

public class ServerDispatcher extends ChannelHandlerAdapter {
	
	private ApplicationContext context; 
	
	private Map<String,Class> actionMap = new HashMap<String,Class>();
	
	private static final Logger logger = LoggerFactory.getLogger(ServerDispatcher.class);
	
	public ServerDispatcher(ApplicationContext context,Map<String,Class> actionMap){
		this.actionMap = actionMap;
		this.context = context;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		Message message = (Message)msg;

		//如果是JSPT发过来的消息，保存计时通道保存JSPT客户端消息通道
		if( message.getHeader().getId() == -1){
			TCPMap.getInstance().getChannelMap().put(JSPTConstants.JSPT_MOBILE, ctx);
			logger.info("保存WEB客户端消息通道");
		}else{ //终端发送过来的消息透传JSPT
			ChannelHandlerContext jsptctx = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_MOBILE);
			if( jsptctx!=null ){
				jsptctx.writeAndFlush(message);
				logger.info("透传终端消息到WEB客户端");
			}
		}
		
		
		//消息处理接口
		String msgId = Integer.toHexString(message.getHeader().getId());
		try{
			//娴犲穬ction map闁插本鐗撮幑鐢禿閹垫儳鍤�电懓绨查惃鍒焎tion婢跺嫮鎮婃稉姘
			Class actionz = actionMap.get(msgId);
			if(actionz != null){
				logger.info("请求消息接口：" + actionz.toString());
				final Action action = (Action)context.getBean(actionz);
				if(action != null){
					final ActionContext actionContext = new ActionContext(context,ctx,message);
					//婢跺嫮鎮婂鍌涱劄
					//娴ｈ法鏁xecutor.inEventLoop()閸掋倖鏌囪ぐ鎾冲缁捐法鈻奸弰顖氭儊閺勭枎ventLoopGroup閻ㄥ嫮鍤庣粙瀣剁礉閸氾箑鍨导姘瘶鐟佸懎銈絋ask娴溿倗绮伴崘鍛村劥缁捐法鈻煎Ч鐘冲⒔鐞涳拷
						if(ctx.executor().inEventLoop()){
							logger.info("in main thread execute action");
							//閹笛嗩攽action
							action.action(actionContext);
						}else{
							ctx.executor().execute(new Runnable(){
									
								public void run() {
									logger.info("in thread execute action");
									//閹笛嗩攽action
									action.action(actionContext);
								}
								
							});
						}
					
				}else{
					logger.warn("not fount action for " + Integer.toHexString(message.getHeader().getId()));
					//Message bmsg = MessageUtils.notFountMessage(message.getHeader().getNumber(),message.getHeader().getId());
					//returnMessage(ctx,bmsg);
				}
			}
			ctx.fireChannelRead(msg);
		}catch(Throwable ex){
			logger.error("error action for " + Integer.toHexString(message.getHeader().getId()),ex);
			Message bmsg = MessageUtils.errorMessage(message.getHeader().getNumber(),message.getHeader().getId());
			returnMessage(ctx,bmsg);
		}
	}
	
	public void returnMessage(ChannelHandlerContext ctx,Message msg){
		
		ctx.writeAndFlush(msg);
	}
	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
		
	}
	



}
