package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 设置计时终端应用参数应答
 * 透传消息ID：0x0501
 * 终端使用此消息应答计时平台下发的设置计时终端应用参数消息，设置计时终端应用参数应答消息数据格式见表B.57。
 * @author dev
 *
 */
public class TimeTerminalSetParam extends TransportObject {
	
	private byte resultCode;	//应答代码 BYTE 1：设置成功 2：设置失败

	public TimeTerminalSetParam() {
		super();
	}
	
	public TimeTerminalSetParam(byte[] bytes) {
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
