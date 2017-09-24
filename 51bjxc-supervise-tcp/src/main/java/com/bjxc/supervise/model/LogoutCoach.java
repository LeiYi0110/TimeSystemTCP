package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class LogoutCoach extends TransportObject {

	private String coachNum;
	private LocationInfo locationInfo;

	public LogoutCoach() {
		super();
	}
	public LogoutCoach(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		
		byte[] coachNumBytes = new byte[16];
		copiedBuffer.readBytes(coachNumBytes);
		
		byte[] locationInfoBytes = new byte[copiedBuffer.readableBytes()];
		copiedBuffer.readBytes(locationInfoBytes);
		
		try{
			coachNum = new String(coachNumBytes,"GBK");
			locationInfo = new LocationInfo(locationInfoBytes);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(coachNum.getBytes("GBK"));
			buffer.writeBytes(locationInfo.getBytes());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	public String getCoachNum() {
		return coachNum;
	}
	public void setCoachNum(String coachNum) {
		this.coachNum = coachNum;
	}
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	@Override
	public String toString() {
		return "LogoutCoach [coachNum=" + coachNum + ", locationInfo=" + locationInfo.toString() + "]";
	}

}
