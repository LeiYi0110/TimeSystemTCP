package com.bjxc.supervise.Action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.model.IBytes;
import com.bjxc.supervise.model.ParamDS;
import com.bjxc.supervise.model.ResponseTerminalQuery;
import com.bjxc.supervise.model.Terminal;
import com.bjxc.supervise.model.TerminalQuery;
import com.bjxc.supervise.model.TransportObject;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.Message;

import io.netty.channel.ChannelHandlerContext;

public class TerminalQueryAction implements Action {
	
	private static final Logger logger = LoggerFactory.getLogger(TerminalQueryAction.class);

	public void action(ActionContext context) {
		logger.info("0x0104 ResponseTerminalQueryAction");
		Message msg = context.getMessag();
		byte[] body = msg.getBody();
		ResponseTerminalQuery terminal = new ResponseTerminalQuery(body);
		System.out.println("getParamNumber: "+terminal.getParamNumber());
		System.out.println("getParamList: "+terminal.getParamList().toString());
	}
}
