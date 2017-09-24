package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResponseStudentLogout extends TransportObject {
	
	private byte resultCode;	//登出结果 1：登出成功；2：登出失败
	private String studentNum;	//学员编号 

	public ResponseStudentLogout() {
		super();
	}
	
	public ResponseStudentLogout(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
		byte[] studentNumBytes = new byte[16];
		copiedBuffer.readBytes(studentNumBytes);
		try {
			studentNum = new String(studentNumBytes, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeByte(resultCode);
			buffer.writeBytes(studentNum.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

}
