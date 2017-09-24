package com.bjxc.jtt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.jtt.model.JttLogin;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.server.TCPMap;

import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class JTTHandler  extends ChannelHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(JTTHandler.class);
	
	private JttMessage heartMessage = null;
	
	public JTTHandler() {
		super();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("连接jtt成功");
		TCPMap.getInstance().getChannelMap().put(JttUtils.JTT_CHANNEL, ctx);
		heartMessage = JttUtils.heartMessage();
		
		//登录
		JttMessage jttMessage = JttUtils.login("119.137.55.243", (short)8585);
		ctx.writeAndFlush(jttMessage);
	};
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("jtt回应");
		TCPMap.getInstance().getChannelMap().put(JttUtils.JTT_CHANNEL, ctx);
		
		UnpooledUnsafeDirectByteBuf buf = (UnpooledUnsafeDirectByteBuf)msg;
		byte[] msgBytes = new byte[buf.readableBytes()];
		buf.readBytes(msgBytes);
		String hex = HexUtils.BinaryToHexString(msgBytes);
		logger.info("jtt返回数据:　"+hex);
		
		logger.info("first=" + hex.substring(0, 2) + ", length=" + hex.substring(2, 10) + ", serialNumber=" + hex.substring(10, 18) + ", type=" + hex.substring(18, 22)
				+ ", code=" + hex.substring(22, 30) + ", version=" + hex.substring(30, 36) + ", encrypt=" + hex.substring(36,38) + ", key=" + hex.substring(38, 46)
				+ ", data, crcCode, last=" + hex.substring(46));
		
/*		LocationInfo defaultLocationInfo = new LocationInfo();
		defaultLocationInfo.setAlertInfo(1);
		defaultLocationInfo.setCarSpeed(12);
		defaultLocationInfo.setDeviceId(3);
		defaultLocationInfo.setElevation(4);
		defaultLocationInfo.setEngine_speed(5);
		defaultLocationInfo.setGasonline_cost(6);
		defaultLocationInfo.setGpsSpeed(7);
		defaultLocationInfo.setId(8);
		defaultLocationInfo.setLatitude(9);
		defaultLocationInfo.setLongtitude(10);
		defaultLocationInfo.setOrientation(11);
		defaultLocationInfo.setStatus(2);
		defaultLocationInfo.setSum_distance(13);
		defaultLocationInfo.setTime("170327161301");
		defaultLocationInfo.setTrainingrecordid(14);
		
		TrainingCar trainingCar = new TrainingCar();
		trainingCar.setCarnum("9867563231");
		trainingCar.setLicnum("粤A0041学");
		trainingCar.setPlatecolor(1);
		
		JttMessage locationTransform = JttUtils.locationTransform(defaultLocationInfo, trainingCar );
		ctx.writeAndFlush(locationTransform);
		
		JttMessage alertTransform = JttUtils.alertTransform(defaultLocationInfo, trainingCar);
		ctx.writeAndFlush(alertTransform);
		
		JttMessage coachTransform = JttUtils.coachTransform("440224198411110272", "李朝升", trainingCar);
		ctx.writeAndFlush(coachTransform);
		
		Device device = new Device();
		Ins ins	 = new Ins();
		ins.setDistrict("110100");
		ins.setName("深圳酷商");
		JttMessage carTransform = JttUtils.carTransform(device, trainingCar, ins);
		ctx.writeAndFlush(carTransform);
		
		File file = new File("C:\\Users\\alogol\\Desktop\\aaaa.jpg");
		byte[] content = JttUtils.getContent(file);
		DeviceImage deviceImage = new DeviceImage();
		deviceImage.setChannelNo(1);
		deviceImage.setDataSize(content.length);
		deviceImage.setImageHeight(320);
		
		JttMessage photoTransform = JttUtils.photoTransform(deviceImage, defaultLocationInfo, trainingCar, content);
		ctx.writeAndFlush(photoTransform);*/
		
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
    				ctx.writeAndFlush(heartMessage);
                    logger.info("发送jtt心跳");
                    break;
                default:
                    break;
            }
        }
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}
