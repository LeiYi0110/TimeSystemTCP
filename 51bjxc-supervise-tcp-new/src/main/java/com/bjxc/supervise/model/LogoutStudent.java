package com.bjxc.supervise.model;

import com.bjxc.supervise.netty.HexUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

public class LogoutStudent extends TransportObject {
	
	private String studentNum;	//学员编号 16个字节
	private String logoutTime;	//登出时间 BCD[6] YYMMDDhhmmss
	private short thisLoginAliveTime; //学员该次登录总时间 单位：min
	private short thisLoginAliveMileage;	//学员该次登录总里程 单位：1/10km
	private int courseId;	//课堂ID DWORD 标识学员的一次培训过程，计时终端自行使用
	private LocationInfo locationInfo; //基本GNSS数据包

	public LogoutStudent() {
		super();
	}
	
	public LogoutStudent(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		byte[] studentNumBytes = new byte[16];
		copiedBuffer.readBytes(studentNumBytes);
		byte[] logoutTimeBytes = new byte[6];
		copiedBuffer.readBytes(logoutTimeBytes);
		logoutTime = HexUtils.bcd2Str(logoutTimeBytes);
		thisLoginAliveTime = copiedBuffer.readShort();
		thisLoginAliveMileage = copiedBuffer.readShort();
		courseId = copiedBuffer.readInt();
		byte[] locationInfoBytes = new byte[copiedBuffer.readableBytes()];
		copiedBuffer.readBytes(locationInfoBytes);
		try {
			studentNum = new String(studentNumBytes,"GBK");
			locationInfo = new LocationInfo(locationInfoBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(studentNum.getBytes("GBK"));
			buffer.writeBytes(HexUtils.str2Bcd(logoutTime));
			buffer.writeShort(thisLoginAliveTime);
			buffer.writeShort(thisLoginAliveMileage);
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

	public short getThisLoginAliveTime() {
		return thisLoginAliveTime;
	}

	public void setThisLoginAliveTime(short thisLoginAliveTime) {
		this.thisLoginAliveTime = thisLoginAliveTime;
	}

	public short getThisLoginAliveMileage() {
		return thisLoginAliveMileage;
	}

	public void setThisLoginAliveMileage(short thisLoginAliveMileage) {
		this.thisLoginAliveMileage = thisLoginAliveMileage;
	}

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return "LogoutStudent [studentNum=" + studentNum + ", logoutTime=" + logoutTime
				+ ", thisLoginAliveTime=" + thisLoginAliveTime + ", thisLoginAliveMileage=" + thisLoginAliveMileage
				+ ", courseId=" + courseId + ", locationInfo=" + locationInfo + "]";
	}

}
