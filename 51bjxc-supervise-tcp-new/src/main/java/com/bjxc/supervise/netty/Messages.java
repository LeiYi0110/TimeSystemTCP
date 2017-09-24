package com.bjxc.supervise.netty;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.Action.UpTransportAction;
import com.bjxc.supervise.Action.Utils;
import com.bjxc.supervise.model.ResendPackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;;

public class Messages {
	private long timestamp;
	private Message[] msgArry;
	
	private Timer timer;
	
//	private int delayTime = 5*1000;
	private int delayTime = 1*1000;
	private int scheduleTime = 3;	//需要发送3次请求补传分包
	
	private ChannelHandlerContext context;
	
	private static final Logger logger = LoggerFactory.getLogger(Messages.class);
	
	public Messages(int size,ChannelHandlerContext context){
		this.msgArry = new Message[size];
		this.timestamp = System.currentTimeMillis();
		this.context = context;
		timer =new Timer();
		
		TimerTask task = new TimerTask() {   
			public void run() {   
				if(scheduleTime > 0 && msgArry.length > 0){
					if (expire()) {
						logger.info("检查分包完整性--");
						ResendPackage resendPackage = new ResendPackage();
						
						short firstNum = (short)(msgArry[0].getHeader().getNumber() + msgArry[0].getHeader().getPackageIndex() - 1);
						resendPackage.setOriginalSerialNum(firstNum);
						
						for(int i = 0 ; i < msgArry.length ; i++)
						{
							if (msgArry[i] == null) {
								logger.info("需要补传的包序号："+(i + 1));
								resendPackage.getParamList().add((short)(i + 1));
							}
						}
						Message message = Utils.getCommonMessage(resendPackage, (short)0x8003, msgArry[0].getHeader().getMobile(),MessageUtils.getCurrentMessageNumber());
						scheduleTime--;
						logger.info("第"+(3-scheduleTime)+"发送补传数据包请求-----firstNum: "+firstNum);
						timestamp = System.currentTimeMillis();
						context.writeAndFlush(message);
					}
				}else{
					timer.cancel();
				}
			}   
		};
		
		timer.schedule(task,delayTime,delayTime);	//判断数据包是否完整
	}
	
	public void addMessage(int index,Message msg){
		this.msgArry[index-1] = msg;
		timestamp = System.currentTimeMillis();
	}
	
	/**
	 * 30秒超时
	 */
	public boolean expire(){
		long currentTime = System.currentTimeMillis();
		long lt = currentTime - timestamp;
		return lt > 30 * 1000;
	}
	
	/**
	 * 瀹屾垚
	 * @return
	 */
	public boolean complete(){
		for(Message msg :  msgArry){
			if(msg == null){
				return false;
			}
		}
		timer.cancel();
		return true;
	}
	
	/**
	 * 灏嗗鏉℃秷鎭殑娑堟伅骞舵帴鎴愪竴涓�
	 * @return
	 */
	public byte[] toBody(){
		if(!this.complete()){
			throw new NullPointerException("message not complete");
		}
		ByteBuf buf = Unpooled.buffer();
		for(Message msg :  msgArry){
			if(msg.getBody() != null){
				buf.writeBytes(msg.getBody());
			}
		}
		byte[] body = new byte[buf.readableBytes()];
		buf.readBytes(body);
		return body;
	}
}
