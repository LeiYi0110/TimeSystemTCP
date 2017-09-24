package com.bjxc.supervise.Action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.model.LocationInfo;
import com.bjxc.supervise.model.Login;
import com.bjxc.supervise.model.ParamBS;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.service.LocationInfoService;

public class LoginAction implements Action {
	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Resource
	private LocationInfoService locationInfoService;

	public void action(ActionContext context) {
		logger.info("0x0201 LoginAction");
		Message msg = context.getMessag();
		byte[] body = msg.getBody();
		
		System.out.println("返回结果码: "+body[0]);
	}
	
}
