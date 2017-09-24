package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResponsePhotoImmediately extends TransportObject {
	private byte upMode;
	private byte cameraNum;
	private byte photoSize;
	
	public ResponsePhotoImmediately(){}
	
	public ResponsePhotoImmediately(byte[] bytes){
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);
		try {
			upMode=hbuf.readByte();
			cameraNum=hbuf.readByte();
			photoSize=hbuf.readByte();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try{
			buffer.writeByte(upMode);
			buffer.writeByte(cameraNum);
			buffer.writeByte(photoSize);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	public byte getUpMode() {
		return upMode;
	}
	public void setUpMode(byte upMode) {
		this.upMode = upMode;
	}
	public byte getCameraNum() {
		return cameraNum;
	}
	public void setCameraNum(byte cameraNum) {
		this.cameraNum = cameraNum;
	}
	public byte getPhotoSize() {
		return photoSize;
	}
	public void setPhotoSize(byte photoSize) {
		this.photoSize = photoSize;
	}

}
