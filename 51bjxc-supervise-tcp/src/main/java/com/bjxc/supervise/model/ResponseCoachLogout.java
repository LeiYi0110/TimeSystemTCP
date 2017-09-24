package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResponseCoachLogout extends TransportObject {
	
	private byte resultCode;
	private String coachNum;
	
	public ResponseCoachLogout() {
		super();
	}
	
	public ResponseCoachLogout(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
		byte[] coachNumBytes = new byte[16];
		copiedBuffer.readBytes(coachNumBytes);
		
		try{
			coachNum = new String(coachNumBytes,"GBK");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeByte(resultCode);
			buffer.writeBytes(coachNum.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	public byte getResultCode() {
		return resultCode;
	}

	public void setResultCode(byte resultCode) {
		this.resultCode = resultCode;
	}

	public String getCoachNum() {
		return coachNum;
	}

	public void setCoachNum(String coachNum) {
		this.coachNum = coachNum;
	}

}
