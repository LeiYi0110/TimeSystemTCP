package com.bjxc.supervise.netty.server;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;

import com.bjxc.model.Province;
import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.client.Client;
import com.bjxc.supervise.netty.client.ProvinceClient;
import com.bjxc.supervise.service.DevAssignService;
import com.bjxc.supervise.service.DeviceService;
import com.bjxc.supervise.service.ProvinceService;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息透传给省平台服力
 * @author cras
 *
 */
public class PassthroughHandler   extends ChannelHandlerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(PassthroughHandler.class);
	
	private ApplicationContext context;
	
	public PassthroughHandler(ApplicationContext context){
		this.context = context;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message)msg;
		logger.info("passthroughHandler message to province: " + Integer.toHexString(message.getHeader().getId()));

//		ProvinceClient client = context.getBean(ProvinceClient.class);
//		client.start();
//		client.writeAndFlush(message);

		//跟省平台保持一个通道连接传送消息
		ChannelHandlerContext provinceContext = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_PLATFORMNO);
		provinceContext.writeAndFlush(msg);
		
		//把转发到省平台的数据保存到数据库
//		Province province = new Province();
//		province.setMsgid(Integer.toHexString(message.getHeader().getId()));
//		if(message.getHeader().getId() == (short)0x0900
//				||message.getHeader().getId() == (short)0x8900){
//			UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody(message.getBody());
//			ExtendMessageBody extendMessageBody = upTransportMessageBody.getExtendMessageBody();
//			province.setExtendmsgid(Integer.toHexString(extendMessageBody.getTransportId()));
//		}
//		province.setMsgcontent(message.getOriginalMessage());
//		province.setCreatetime(new Date());
//		
//		ApplicationContext applicationContext = SpringApplicationContext.getInstance().getContext();
//		ProvinceService provinceService = applicationContext.getBean(com.bjxc.supervise.service.ProvinceService.class);
//		provinceService.add(province);
	}
}
