package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * A.1.1.1.1　	命令上报学时记录应答
 * 透传消息ID：0x0205
 * 客户端回复服务器下发的命令上报学时记录消息时，使用该消息2次回复服务器端，
 * 第一次应答0x01开始查询，第二次根据查询结果应答0x02-0x04反馈查询执行结果。
 * 查询结果的反馈通过透传消息ID0x0203实现。命令上报学时记录应答消息数据格式见表B.44。
 * @author dev
 *
 */
public class StudyingTimeOrder extends TransportObject {
	
	private byte resultCode;	//执行结果 1：查询的记录正在上传；2：SD卡没有找到；3：执行成功，但无指定记录；4：执行成功，稍候上报查询结果

	public StudyingTimeOrder() {
		super();
	}
	
	public StudyingTimeOrder(byte[] bytes) {
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
