package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 设置禁训状态
 * 透传消息ID：0x8502
 * 应答属性:0x01
 * 服务器端使用此消息设置计时终端的禁训状态，设置禁训状态消息数据格式见表B.58。

 * @author dev
 *
 */
public class ResponseUnableTraining extends TransportObject {
	
	private byte unableTrainingStatus;
	private byte messageLen;
	private String message;

	public ResponseUnableTraining() {
		super();
	}
	
	public ResponseUnableTraining(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		unableTrainingStatus = copiedBuffer.readByte();
		messageLen = copiedBuffer.readByte();
		byte[] messageBytes= new byte[copiedBuffer.readableBytes()];
		copiedBuffer.readBytes(messageBytes);
		try {
			message = new String(messageBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(unableTrainingStatus);
		try {
			if(message == null){
				buffer.writeByte(0);
			}else{
				buffer.writeByte(message.getBytes("GBK").length);
				buffer.writeBytes(message.getBytes("GBK"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public byte getUnableTrainingStatus() {
		return unableTrainingStatus;
	}

	public void setUnableTrainingStatus(byte unableTrainingStatus) {
		this.unableTrainingStatus = unableTrainingStatus;
	}

	public byte getMessageLen() {
		return messageLen;
	}

	public void setMessageLen(byte messageLen) {
		this.messageLen = messageLen;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
