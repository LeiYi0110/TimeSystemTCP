package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResponseStudentLogin extends TransportObject {
	
	private byte resultCode;	//登录结果代码 1：登录成功 2：无效的学员编号；3：禁止登录的学员；4：区域外教学提醒；5：准教车型与培训车型不符；9：其他错误
	private String studentNum;	//学员编号 byte[16]
	private short wholeClassHour;	//总培训学时 单位：min
	private short currentClassHour;	//当前培训部分已完成学时 单位：min
	private short wholeMileage;	//总培训里程 单位：1/10km
	private short currentMileage;	//当前培训部分已完成里程单位：1/10km
	private byte hasExtalMsg;	//是否报读附加消息 0：不必报读；1：需要报读
	private byte extalMsgLen;	//附加消息长度 长度为n，无附加数据则为0
	private String extalMsg;	//附加消息 最大254Bytes

	public ResponseStudentLogin() {
		super();
	}
	
	public ResponseStudentLogin(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
		byte[] studentNumBytes = new byte[16];
		copiedBuffer.readBytes(studentNumBytes);
		try {
			studentNum = new String(studentNumBytes,"GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		wholeClassHour = copiedBuffer.readShort();
		currentClassHour = copiedBuffer.readShort();
		wholeMileage = copiedBuffer.readShort();
		currentMileage = copiedBuffer.readShort();
		hasExtalMsg = copiedBuffer.readByte();
		extalMsgLen = copiedBuffer.readByte();
		if(extalMsgLen>0){
			byte[] extalMsgBytes = new byte[copiedBuffer.readableBytes()];
			copiedBuffer.readBytes(extalMsgBytes);
			try {
				extalMsg = new String(extalMsgBytes,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeByte(resultCode);
			buffer.writeBytes(studentNum.getBytes("GBK"));
			buffer.writeShort(wholeClassHour);
			buffer.writeShort(currentClassHour);
			buffer.writeShort(wholeMileage);
			buffer.writeShort(currentMileage);
			buffer.writeByte(hasExtalMsg);
			if(hasExtalMsg==0){	//不必报读附加信息
				buffer.writeByte(0);
			}else{
				buffer.writeByte(extalMsg.getBytes("GBK").length);
				buffer.writeBytes(extalMsg.getBytes("GBK"));
			}
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

	public short getWholeClassHour() {
		return wholeClassHour;
	}

	public void setWholeClassHour(short wholeClassHour) {
		this.wholeClassHour = wholeClassHour;
	}

	public short getCurrentClassHour() {
		return currentClassHour;
	}

	public void setCurrentClassHour(short currentClassHour) {
		this.currentClassHour = currentClassHour;
	}

	public short getWholeMileage() {
		return wholeMileage;
	}

	public void setWholeMileage(short wholeMileage) {
		this.wholeMileage = wholeMileage;
	}

	public short getCurrentMileage() {
		return currentMileage;
	}

	public void setCurrentMileage(short currentMileage) {
		this.currentMileage = currentMileage;
	}

	public byte getHasExtalMsg() {
		return hasExtalMsg;
	}

	public void setHasExtalMsg(byte hasExtalMsg) {
		this.hasExtalMsg = hasExtalMsg;
	}

	public byte getExtalMsgLen() {
		return extalMsgLen;
	}

	public void setExtalMsgLen(byte extalMsgLen) {
		this.extalMsgLen = extalMsgLen;
	}

	public String getExtalMsg() {
		return extalMsg;
	}

	public void setExtalMsg(String extalMsg) {
		this.extalMsg = extalMsg;
	}

}
