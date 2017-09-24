package com.bjxc.supervise.Action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.bjxc.Result;
import com.bjxc.supervise.http.crypto.HttpClientService;
import com.bjxc.supervise.model.LoginInfo;
import com.bjxc.supervise.model.OperationLog;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.server.TCPMap;
import com.bjxc.supervise.service.DevAssignService;
import com.bjxc.supervise.service.OperationLogService;

public class DeregisterAction implements Action{
	
	private static final Logger logger = LoggerFactory.getLogger(DeregisterAction.class);
	@Resource
	private DevAssignService devAssignService;
	@Resource
	private HttpClientService httpClientService;
	@Resource
	private OperationLogService operationLogService;

	@Value("${bjxc.jspt.http.url}")
	private String jsptHttpUrl;
	
	public void action(ActionContext actionContext) {
		logger.info("0x0100 DeregisterAction");
		Message msg = actionContext.getMessag();
		String mobile = msg.getHeader().getMobile();
		logger.info("终端注销: "+ mobile);
		Message result = MessageUtils.successMessage(msg.getHeader().getId(), msg.getHeader().getNumber());
		actionContext.writeAndFlush(result);

		//向省平台解除绑定关系
		LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("devnum", loginInfo.getDevice().getDevnum());
		params.put("carnum", loginInfo.getTrainingCar().getTrainingCarNum());
		params.put("sim", msg.getHeader().getMobile().substring(5));
		
		//TODO:临时
//		devAssignService.removeByMobile(mobile);
		
		//TODO:临时注释
		try {
			Result result2 = httpClientService.doPost(jsptHttpUrl+"/province/devrembinding", params);
			if(result2.getErrorCode()==0){
					logger.info("http解除绑定成功");
					//移除数据库中绑定关系
					devAssignService.removeByMobile(mobile);
					//添加日志记录
					OperationLog operationLog = new OperationLog();
					operationLog.setInsId(2);
					operationLog.setLogEvent("TCP终端注销："+loginInfo.getDevice().getDevnum());
					operationLog.setLogTime(new Date());
					operationLog.setLogUser("终端");
					operationLog.setRemark("");
					operationLogService.add(operationLog);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}
