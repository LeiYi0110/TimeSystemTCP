package com.bjxc.supervise.Action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bjxc.json.JacksonBinder;
import com.bjxc.jtt.JttMessage;
import com.bjxc.jtt.JttUtils;
import com.bjxc.model.MessageModel;
import com.bjxc.model.TrainingCar;
import com.bjxc.supervise.http.crypto.HttpClientService;
import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.model.LocationInfo;
import com.bjxc.supervise.model.LoginInfo;
import com.bjxc.supervise.model.ParamBS;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.server.TCPMap;
import com.bjxc.supervise.service.LocationInfoService;

import io.netty.channel.ChannelHandlerContext;

public class LocationQueryAction implements Action {
	private static final Logger logger = LoggerFactory.getLogger(LocationQueryAction.class);
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	
	@Resource
	private LocationInfoService locationInfoService;

	@Autowired
	private HttpClientService httpClientService;

	@Value("${bjxc.message.server}")
	private String messageServer;

	public void action(ActionContext context) {
		logger.info("0x0200/0x0201 LocationInfoAction");
		Message msg = context.getMessag();
		byte[] body = msg.getBody();
		LocationInfo locationInfo = new LocationInfo(body);
		logger.info("0x0200/0x0201 : "+locationInfo.toString());
		
		com.bjxc.model.LocationInfo insertLocationInfo = Utils.insertLocationInfo(locationInfo,msg.getHeader().getMobile());
		
		Message createDefaultMessage = MessageUtils.successMessage(msg.getHeader().getId(), msg.getHeader().getNumber());
		context.writeAndFlush(createDefaultMessage);
		
		LoginInfo loginInfo = 	TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
		
		//把处理过的位置信息传到WEB
		MessageModel messageModel = new MessageModel();
		messageModel.setBody(insertLocationInfo);
		messageModel.setMessageIdHexString("-2");
		try {
			String insCode = loginInfo.getIns().getInscode();
			httpClientService.doSimpleGet(messageServer +"/message?inscode="+insCode+"&content="+URLEncoder.encode(binder.toJson(messageModel), "UTF-8"));
			logger.info("透传终端消息到WEB客户端-处理过的位置信息");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//位置信息, 报警信息转发809协议
		ChannelHandlerContext jttChannel = TCPMap.getInstance().getChannelMap().get(JttUtils.JTT_CHANNEL);
		if(jttChannel != null){
			TrainingCar jttTrainingCar = loginInfo.getTrainingCar();
			
			JttMessage jttMessage = JttUtils.locationTransform(insertLocationInfo,jttTrainingCar);
			jttChannel.writeAndFlush(jttMessage);
			
			JttMessage alertTransform = JttUtils.alertTransform(insertLocationInfo, jttTrainingCar);
			jttChannel.writeAndFlush(alertTransform);
		}else{
			logger.info("jtt通道为空");
		}
	}
}
