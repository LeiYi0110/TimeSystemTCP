package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class LocationTemporary extends TransportObject {
	
	private short timeInterval;	//时间间隔 WORD 单位为s，0则停止跟踪。停止跟踪无需带后继字段
	private int timeValidity;	//位置跟踪有效期 DWORD 单位为s，终端在接收到位置跟踪控制消息后，在有效期截止时间之前，依据消息中的时间间隔发送位置汇报

	public LocationTemporary() {
		super();
	}
	
	public LocationTemporary(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		timeInterval = copiedBuffer.readShort();
		if(timeInterval != 0){
			timeValidity = copiedBuffer.readInt();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeShort(timeInterval);
		if(timeInterval != 0){
			buffer.writeInt(timeValidity);
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public short getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(short timeInterval) {
		this.timeInterval = timeInterval;
	}

	public int getTimeValidity() {
		return timeValidity;
	}

	public void setTimeValidity(int timeValidity) {
		this.timeValidity = timeValidity;
	}

}
