package com.bjxc.supervise.netty.server;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bjxc.jtt.JttMessage;
import com.bjxc.jtt.JttUtils;
import com.bjxc.model.DevAssignInfo;
import com.bjxc.model.Ins;
import com.bjxc.supervise.http.crypto.DecodeUtil;
import com.bjxc.supervise.model.Device;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.LoginInfo;
import com.bjxc.supervise.model.OperationLog;
import com.bjxc.supervise.model.TrainingCar;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.service.DevAssignService;
import com.bjxc.supervise.service.DeviceService;
import com.bjxc.supervise.service.InsService;
import com.bjxc.supervise.service.OperationLogService;
import com.bjxc.supervise.service.TrainingCarService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author cras
 *
 */
public class LoginAuthRequestHandler extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginAuthRequestHandler.class);
	private boolean channelSecurity = false;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("LoginAuthRequestHandler------------------");
		Message message = (Message)msg;
		
		String socketString = ctx.channel().remoteAddress().toString();  
		logger.info("消息来源："+socketString);
		if( message.getHeader() != null){
			logger.info("message id："+Integer.toHexString(message.getHeader().getId()));
		}
		logger.info("message："+HexUtils.BinaryToHexString(message.getBytes()));

		try{
			if(message.getHeader() != null && message.getHeader().getId() == 0x0102){
				
				ByteBuf tranBuf = Unpooled.copiedBuffer(message.getBody());
				logger.info("鉴权消息体："+HexUtils.BinaryToHexString(tranBuf.array()));

				//读取时间戳
				int timestamp = tranBuf.readInt();
				logger.info("timetmp:" + timestamp);

				//读取鉴权密文
				byte[] ectxt = new byte[tranBuf.readableBytes()];
				tranBuf.readBytes(ectxt);

				String encodedEncryptedStr = HexUtils.BinaryToHexString(ectxt);
				
				ApplicationContext applicationContext = SpringApplicationContext.getInstance().getContext();
				DeviceService deviceService = applicationContext.getBean(com.bjxc.supervise.service.DeviceService.class);
				InsService insService = applicationContext.getBean(com.bjxc.supervise.service.InsService.class);
				TrainingCarService trainingCarService = applicationContext.getBean(com.bjxc.supervise.service.TrainingCarService.class);
				DevAssignService devAssignService = applicationContext.getBean(com.bjxc.supervise.service.DevAssignService.class);
				
				//检查数据库中是否注册设备
				DevAssignInfo devAssignByMobile = devAssignService.getDevAssignByMobile(message.getHeader().getMobile());
				if(devAssignByMobile!=null){	//已注册的设备
					
					logger.info("已注册设备：Mobile=" + message.getHeader().getMobile() + "; Devive ID=" + devAssignByMobile.getDeviceId() );
					
					//获取设备信息
					Device terminalDevice = deviceService.getDevice(devAssignByMobile.getDeviceId());
					com.bjxc.model.TrainingCar jttTrainingCar = trainingCarService.getTrainingCarById(devAssignByMobile.getTrainingcarId());
					
					//TODO:临时注释 校验鉴权密文
					byte [] data=devAssignByMobile.getDevNum().getBytes("GBK");
					boolean checkresult = DecodeUtil.checkTerminalAuth(data, encodedEncryptedStr, timestamp, terminalDevice.getKey(), terminalDevice.getPasswd());
					logger.info("校验鉴权密文. Result = " + checkresult);
					
					//TODO:临时
//					boolean checkresult = true;
					
					if(checkresult){
						Ins insById = insService.getInsById(terminalDevice.getInsId());
						
						//保存注册登录信息
						LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(message.getHeader().getMobile());
						if(loginInfo == null){
							loginInfo = new LoginInfo();
						}
						TrainingCar trainingCar = new TrainingCar();
						trainingCar.setTrainingCarId(devAssignByMobile.getTrainingcarId());
						trainingCar.setTrainingLicNum(devAssignByMobile.getLicNum());
						trainingCar.setTrainingCarNum(devAssignByMobile.getCarNum());
						loginInfo.setTrainingCar(trainingCar);
						loginInfo.setDevice(terminalDevice);
						loginInfo.setCadata(terminalDevice.getKey());
						loginInfo.setCapwd(terminalDevice.getPasswd());
						loginInfo.setMapMode(devAssignByMobile.getMapType());
						loginInfo.setJttTrainingCar(jttTrainingCar);
						loginInfo.setIns(insById);
						
						
						TCPMap.getInstance().getLoginInfoMap().put(message.getHeader().getMobile(),loginInfo);
						
						//鉴权成功
						channelSecurity = true;
						
						//添加日志记录
						OperationLogService operationLogService = applicationContext.getBean(com.bjxc.supervise.service.OperationLogService.class);
						OperationLog operationLog = new OperationLog();
						operationLog.setInsId(2);
						operationLog.setLogEvent("TCP终端鉴权："+devAssignByMobile.getDevNum());
						operationLog.setLogTime(new Date());
						operationLog.setLogUser("终端");
						operationLog.setRemark("");
						operationLogService.add(operationLog);
						
						//保存终端消息通道
						TCPMap.getInstance().getChannelMap().put(message.getHeader().getMobile(), ctx);
						
						Message result = MessageUtils.successMessage(message.getHeader().getId(), message.getHeader().getNumber());
						ctx.writeAndFlush(result);
						
						//车辆静态数据转发809协议
						ChannelHandlerContext jttChannel = TCPMap.getInstance().getChannelMap().get(JttUtils.JTT_CHANNEL);
						if(jttChannel != null){
							JttMessage jttMessage = JttUtils.carTransform(terminalDevice, jttTrainingCar,insById);
							jttChannel.writeAndFlush(jttMessage);
						}
						
					}else{
						Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
						ctx.writeAndFlush(result);

						//鉴权失败返回不进行后续操作
						return;

					}
				}else{
					logger.info("鉴权： 该手机号尚未注册！");
					Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
					ctx.writeAndFlush(result);
					
					//鉴权失败返回不进行后续操作
					return;
				}
				message.getHeader().setSecurity(channelSecurity);
				
			}else if(message.getHeader() != null && message.getHeader().getId() == 0x0900){
				
				//TODO:临时注释
				logger.info("终端扩展驾培协议："+message.getHeader().getId());
				LoginInfo loginInfo = 	TCPMap.getInstance().getLoginInfoMap().get(message.getHeader().getMobile());
				if( loginInfo == null){
					logger.info("LoginInfo is null.");
				}else{
					/*UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody(message.getBody());
					ExtendMessageBody extendMessageBody = upTransportMessageBody.getExtendMessageBody();
					byte[] check = extendMessageBody.getCheck();
					byte[] extendBody = extendMessageBody.getCheckBytes();
					
					//获取校验串
					String encodedEncryptedStr = HexUtils.BinaryToHexString(check);
					
					boolean checkresult = DecodeUtil.checkTerminalAuth(extendBody, encodedEncryptedStr,0, loginInfo.getCadata(), loginInfo.getCapwd());

					logger.info("校验密文. checkresult = " + checkresult);

					if( checkresult){
						//校验密文成功，继续往下走
					}else{
						//校验密文不成功，返回错误消息给终端，并且不处理该条消息
						Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
						ctx.writeAndFlush(result);
						//校验密文失败后不进行后续操作，明天测试
						return;
					}*/
				}
			}
			
			//进行下一步的处理
			ctx.fireChannelRead(msg);
			
		}catch(Throwable ex){
			logger.error("message loginAuth hander" + Integer.toHexString(message.getHeader().getId()), ex);
			Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
			ctx.writeAndFlush(result);
		}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		ctx.close();
	}
	
}
