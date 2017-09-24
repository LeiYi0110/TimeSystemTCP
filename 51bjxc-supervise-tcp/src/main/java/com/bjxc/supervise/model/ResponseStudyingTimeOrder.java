package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * A.1.1.1.1　	命令上报学时记录
 * 透传消息ID：0x8205
 * 应答属性：0x01
 * 服务器下发指令请求客户端上报学时记录，命令上报学时记录消息数据格式见表B.43。
 * @author dev
 *
 */
public class ResponseStudyingTimeOrder extends TransportObject {
	
	private byte queryMode;	//查询方式 BYTE 1：按时间上传；2：按条数上传
	private byte[] queryStartTime;	//查询起始时间 BCD[6] YYMMDDhhmmss
	private byte[] queryEndTime;	//查询终止时间 BCD[6] YYMMDDhhmmss
	private short queryNumber;	//查询条数

	public ResponseStudyingTimeOrder() {
		super();
	}
	
	public ResponseStudyingTimeOrder(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		queryMode = copiedBuffer.readByte();
		queryStartTime = new byte[6];
		copiedBuffer.readBytes(queryStartTime);
		queryEndTime = new byte[6];
		copiedBuffer.readBytes(queryEndTime);
		queryNumber = copiedBuffer.readShort();
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(queryMode);
		buffer.writeBytes(queryStartTime);
		buffer.writeBytes(queryEndTime);
		buffer.writeShort(queryNumber);
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

	public byte[] getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(byte[] queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public byte[] getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(byte[] queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public short getQueryNumber() {
		return queryNumber;
	}

	public void setQueryNumber(short queryNumber) {
		this.queryNumber = queryNumber;
	}

}
