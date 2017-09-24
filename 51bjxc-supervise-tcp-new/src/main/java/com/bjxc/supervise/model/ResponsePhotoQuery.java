package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 透传消息ID：0x8302
 * 应答属性：0x01
 * 服务器端下发查询指令请求查询客户端存储的照片，查询照片消息数据格式见表B.47
 * @author dev
 *
 */
public class ResponsePhotoQuery extends TransportObject {
	
	private byte queryMode;	//查询方式 BYTE 1：按时间查询
	private byte[] startTime;	//查询开始时间 BCD[6] YYMMDDhhmmss
	private byte[] endTime;	//查询结束时间 BCD[6] YYMMDDhhmmss
	
	public ResponsePhotoQuery() {
		super();
	}
	
	public ResponsePhotoQuery(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		queryMode = copiedBuffer.readByte();
		startTime = new byte[6];
		copiedBuffer.readBytes(startTime);
		endTime = new byte[6];
		copiedBuffer.readBytes(endTime);
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(queryMode);
		buffer.writeBytes(startTime);
		buffer.writeBytes(endTime);
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public byte getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(byte queryMode) {
		this.queryMode = queryMode;
	}

	public byte[] getStartTime() {
		return startTime;
	}

	public void setStartTime(byte[] startTime) {
		this.startTime = startTime;
	}

	public byte[] getEndTime() {
		return endTime;
	}

	public void setEndTime(byte[] endTime) {
		this.endTime = endTime;
	}

}
