package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 透传消息ID：0x8303
 * 服务器端使用该消息应答客户端的上报照片查询结果消息，客户端应等待收到服务器端的该应答消息后再发下一数据包。上报照片查询结果应答消息数据格式见表B.50。
 * @author dev
 *
 */
public class ResponsePhotoUpQuery extends TransportObject {
	
	private byte resultCode;	//应答代码 BYTE 0：默认应答 1：停止上报，终端收到“停止上报”应答后则停止查询结果的上报。

	public ResponsePhotoUpQuery() {
		super();
	}
	
	public ResponsePhotoUpQuery(byte[] bytes) {
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
