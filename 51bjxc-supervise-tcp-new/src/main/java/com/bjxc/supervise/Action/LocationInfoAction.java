package com.bjxc.supervise.Action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.model.LocationInfo;
import com.bjxc.supervise.model.ParamBS;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;

public class LocationInfoAction implements Action {
	
	private static final Logger logger = LoggerFactory.getLogger(LocationInfoAction.class);

	public void action(ActionContext context) {
		logger.info("0x0200 LocationInfoAction");
		Message msg = context.getMessag();
		byte[] body = msg.getBody();
		LocationInfo locationInfo = new LocationInfo(body);
		
		System.out.println("status: "+locationInfo.getStatus());
		System.out.println("alertInfo: "+locationInfo.getAlertInfo());
		System.out.println("getCarSpeed: "+locationInfo.getCarSpeed());
		System.out.println("getLatitude: "+locationInfo.getLatitude());
		System.out.println("getLongtitude: "+locationInfo.getLongtitude());
		System.out.println("getGpsSpeed: "+locationInfo.getGpsSpeed());
		System.out.println("getOrientation: "+locationInfo.getOrientation());
		List<ParamBS> paramList = locationInfo.getParamList();
		for (ParamBS paramBS : paramList) {
			System.out.println("getExtal: "+paramBS.toString());
		}
		
		Message createDefaultMessage = MessageUtils.successMessage(msg.getHeader().getId(), msg.getHeader().getNumber());
		context.writeAndFlush(createDefaultMessage);
	}
}
