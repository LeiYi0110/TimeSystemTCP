package com.bjxc.supervise.Action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.model.DownTransportMessageBody;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.LoginCoach;
import com.bjxc.supervise.model.ResponseCoachLogin;
import com.bjxc.supervise.model.TransportObject;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.Message;
import com.sun.org.apache.xerces.internal.xinclude.XInclude11TextReader;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DownTransportAction implements Action {
	
	private static final Logger logger = LoggerFactory.getLogger(DownTransportAction.class);

	public void action(ActionContext context) {
		
		logger.info("0x8900 DownTransportAction");
		Message msg = context.getMessag();
		byte[] body = msg.getBody();
		ByteBuf hbuf = Unpooled.copiedBuffer(body);
		DownTransportMessageBody downTransportMessageBody = new DownTransportMessageBody(body);
		ExtendMessageBody extendMessageBody = downTransportMessageBody.getExtendMessageBody();
		ResponseCoachLogin responseCoachLogin = (ResponseCoachLogin) extendMessageBody.getData();
		System.out.println(responseCoachLogin.getCoachNum());
		System.out.println(responseCoachLogin.getHasExtraMsg());
		System.out.println(responseCoachLogin.getExtraMsgLen());
		System.out.println(responseCoachLogin.getExtraMsg());
		
	}

}
