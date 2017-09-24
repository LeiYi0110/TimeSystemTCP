package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 网页传学员过来伪造科目二科目三学车记录
 * @author John
 *
 */
public class FakeStudent extends TransportObject{
	private String StudentNum;
	private String coachNum;
	private int count;
	
	public FakeStudent() {
		super();
	}
	
	public FakeStudent(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		byte[] studentNumBytes = new byte[16];
		copiedBuffer.readBytes(studentNumBytes);
		byte[] coachNumBytes = new byte[16];
		copiedBuffer.readBytes(coachNumBytes);
		if(copiedBuffer.readableBytes()>0){
			count = copiedBuffer.readInt();
		}
		try {
			StudentNum = new String(studentNumBytes,"GBK");
			coachNum = new String(coachNumBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(StudentNum.getBytes("GBK"));
			buffer.writeBytes(coachNum.getBytes("GBK"));
			buffer.writeInt(count);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	public String getStudentNum() {
		return StudentNum;
	}
	public void setStudentNum(String studentNum) {
		StudentNum = studentNum;
	}
	public String getCoachNum() {
		return coachNum;
	}
	public void setCoachNum(String coachNum) {
		this.coachNum = coachNum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
