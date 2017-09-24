package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 设置禁训状态应答
 * 透传消息ID：0x0502
 * 终端端使用此消息应答计时平台下发的设置禁训状态消息，设置禁训状态应答消息数据格式见表B.59。
 * @author dev
 *
 */
public class UnableTraining extends TransportObject {
	
	private byte resultCode;
	private byte unableTrainingStatus;
	private byte messageLen;
	private String message;

	public UnableTraining() {
		super();
	}
	
	public UnableTraining(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
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
		buffer.writeByte(resultCode);
		buffer.writeByte(unableTrainingStatus);
		buffer.writeByte(messageLen);
		try {
			buffer.writeBytes(message.getBytes("GBK"));
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

	@Override
	public String toString() {
		return "UnableTraining [resultCode=" + resultCode + ", unableTrainingStatus=" + unableTrainingStatus
				+ ", messageLen=" + messageLen + ", message=" + message + "]";
	}

}
