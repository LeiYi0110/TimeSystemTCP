package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 透传消息ID：0x0302
 * 客户端使用该消息回复服务器端下发的查询照片指令，终端应答后开始查询，并通过透传消息ID0x0303开始反馈查询结果。查询照片应答消息数据格式见表B.48。
 * @author dev
 *
 */
public class PhotoQuery extends TransportObject {
	
	private byte resultCode;	//执行结果 BYTE 1：开始查询 2：执行失败

	public PhotoQuery() {
		super();
	}
	
	public PhotoQuery(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
	}

	public byte getResultCode() {
		return resultCode;
	}

	public void setResultCode(byte resultCode) {
		this.resultCode = resultCode;
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(resultCode);
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	@Override
	public String toString() {
		return "PhotoQuery [resultCode=" + resultCode + "]";
	}

}
