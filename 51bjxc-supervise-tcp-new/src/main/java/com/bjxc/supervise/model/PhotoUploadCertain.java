package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 透传消息ID：0x0304
 * 客户端使用该消息应答服务器端下发的上传指定照片消息，应答后使用透传消息ID0x0305和0x0306开始实际数据的上报，上传指定照片应答消息数据格式见表B.52。
 * @author dev
 *
 */
public class PhotoUploadCertain extends TransportObject {
	
	private byte resultCode;	//应答代码 BYTE 0：找到照片，稍候上传 1：没有该照片

	public PhotoUploadCertain() {
		super();
	}
	
	public PhotoUploadCertain(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(resultCode);
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

}
