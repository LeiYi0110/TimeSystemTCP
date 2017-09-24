package com.bjxc.supervise.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.PhotoUploadInit;
import com.bjxc.supervise.model.ResponsePhotoImmediately;
import com.bjxc.supervise.model.TransportObject;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.Message;

public class PhotoAction implements Action {

	private static final Logger logger = LoggerFactory.getLogger(PhotoAction.class);
	public void action(ActionContext context) {
		logger.info("0x7800 askPhoto");
		ResponsePhotoImmediately photo=new ResponsePhotoImmediately();
		photo.setUpMode((byte)1);
		photo.setCameraNum((byte)0);
		photo.setPhotoSize((byte)0x04);
		Message askMessage=getDefaultResponseTransportMessage(photo,(short)0x8301,"��������");
		context.writeAndFlush(askMessage);
	}
	
	private Message getDefaultResponseTransportMessage(TransportObject data,short transportId,String checkString ){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)1);
		extendMessageBody.setDeviceNo("1234560123456789");
		extendMessageBody.setData(data);
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);
		  
		Message message = new Message();
		Header header = new Header();
		header.setId((short)0x8900);
		
		header.setMobile("15019252925");
		header.setNumber((short)1);
		message.setHeader(header);
		  
		message.setBody(upTransportMessageBody.getBytes());
		return message;
	}
}
