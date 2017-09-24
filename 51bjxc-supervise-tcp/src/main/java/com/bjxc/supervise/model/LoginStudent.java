package com.bjxc.supervise.model;

import com.bjxc.supervise.netty.HexUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

public class LoginStudent extends TransportObject {
	
	private String studentNum;	//学员编号 16个字节
	private String coachNum;	//教练编号 16个字节
	private String course;	//培训课程 BCD[5] 课程编码见A4.2
	private int courseId;	//课程ID DWORD 标识学员的一次培训过程，计时终端自行使用
	private LocationInfo locationInfo; //基本GNSS数据包
	
	public LoginStudent() {
		super();
	}
	
	public LoginStudent(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		byte[] studentNumBytes = new byte[16];
		copiedBuffer.readBytes(studentNumBytes);
		byte[] coachNumBytes = new byte[16];
		copiedBuffer.readBytes(coachNumBytes);
		byte[] courseBytes = new byte[5];
		copiedBuffer.readBytes(courseBytes);
		course = HexUtils.bcd2Str(courseBytes);
		courseId = copiedBuffer.readInt();
		byte[] loactionBytes = new byte[copiedBuffer.readableBytes()];
		copiedBuffer.readBytes(loactionBytes);
		
		try {
			studentNum = new String(studentNumBytes,"GBK");
			coachNum = new String(coachNumBytes,"GBK");
			locationInfo = new LocationInfo(loactionBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(studentNum.getBytes("GBK"));
			buffer.writeBytes(coachNum.getBytes("GBK"));
			buffer.writeBytes(HexUtils.str2Bcd(course));
			buffer.writeInt(courseId);
			buffer.writeBytes(locationInfo.getBytes());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getCoachNum() {
		return coachNum;
	}

	public void setCoachNum(String coachNum) {
		this.coachNum = coachNum;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}

	@Override
	public String toString() {
		return "LoginStudent [studentNum=" + studentNum + ", coachNum=" + coachNum + ", course="
				+ course + ", courseId=" + courseId + ", locationInfo=" + locationInfo + "]";
	}

}
