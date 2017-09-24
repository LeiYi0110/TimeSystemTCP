package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResponseCoachLogin extends TransportObject {
	
	private byte resultCode;
	private String coachNum;
	private byte hasExtraMsg;
	private byte extraMsgLen;
	private String extraMsg;

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try{
			buffer.writeByte(resultCode);
			buffer.writeBytes(coachNum.getBytes("GBK"));
			buffer.writeByte(hasExtraMsg);
			if(extraMsg==null){
				buffer.writeByte(0);
			}else{
				buffer.writeByte(extraMsg.getBytes("GBK").length);
				buffer.writeBytes(extraMsg.getBytes("GBK"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public ResponseCoachLogin() {
		super();
	}
	
	public ResponseCoachLogin(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		
		resultCode = copiedBuffer.readByte();
		
		byte[] coachNumBytes = new byte[16];
		copiedBuffer.readBytes(coachNumBytes);
		try{
			coachNum = new String(coachNumBytes,"GBK");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		hasExtraMsg = copiedBuffer.readByte();
		extraMsgLen = copiedBuffer.readByte();
		
		if(extraMsgLen>0){
			byte[] extraMsgBytes = new byte[copiedBuffer.readableBytes()];
			copiedBuffer.readBytes(extraMsgBytes);
			try {
				extraMsg = new String(extraMsgBytes,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
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

	public byte getHasExtraMsg() {
		return hasExtraMsg;
	}

	public void setHasExtraMsg(byte hasExtraMsg) {
		this.hasExtraMsg = hasExtraMsg;
	}

	public byte getExtraMsgLen() {
		return extraMsgLen;
	}

	public void setExtraMsgLen(byte extraMsgLen) {
		this.extraMsgLen = extraMsgLen;
	}

	public String getExtraMsg() {
		return extraMsg;
	}

	public void setExtraMsg(String extraMsg) {
		this.extraMsg = extraMsg;
	}

}
