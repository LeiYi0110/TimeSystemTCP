package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResponsePhotoUploadCertain extends TransportObject {
	
	private String photoNum;	//照片编号 BYTE[10] 计时终端自行编号，仅使用0-9

	public ResponsePhotoUploadCertain() {
		super();
	}
	
	public ResponsePhotoUploadCertain(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		byte[] photoNumBytes = new byte[10];
		copiedBuffer.readBytes(photoNumBytes);
		try {
			photoNum = new String(photoNumBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(photoNum.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public String getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(String photoNum) {
		this.photoNum = photoNum;
	}

}
