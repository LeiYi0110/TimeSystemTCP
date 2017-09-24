package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class LoginCoach extends TransportObject  {
	
	private String coachNum;
	
	private String coachIdentifierNo;
	
	private String teachCarType;
	
	private LocationInfo locationInfo;
	

	public String getCoachNum() {
		return coachNum;
	}

	public void setCoachNum(String coachNum) {
		this.coachNum = coachNum;
	}

	public String getCoachIdentifierNo() {
		return coachIdentifierNo;
	}

	public void setCoachIdentifierNo(String coachIdentifierNo) {
		this.coachIdentifierNo = coachIdentifierNo;
	}

	public String getTeachCarType() {
		return teachCarType;
	}

	public void setTeachCarType(String teachCarType) {
		this.teachCarType = teachCarType;
	}

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	
	
	public byte[] getBytes()
	{
		ByteBuf coachLoginBuffer = Unpooled.buffer();
		  
		  try
		  {
			  //String coachNum = "1234560123456789";
			  coachLoginBuffer.writeBytes(coachNum.getBytes("GBK"));
			  
			 // String coachIdentiferNo = "123456780123456789";
			  coachLoginBuffer.writeBytes(coachIdentifierNo.getBytes("GBK"));
			  
			 // String teachCarType = "C1";
			  coachLoginBuffer.writeBytes(teachCarType.getBytes("GBK"));
			  /*
			  LocationInfo locationInfo = new LocationInfo();
			  locationInfo.setAlertInfo(0);
			  locationInfo.setCarSpeed(1);
			  locationInfo.setGpsSpeed(2);
			  locationInfo.setLatitude(3);
			  locationInfo.setLongtitude(4);
			  locationInfo.setOrientation(5);
			  locationInfo.setStatus(6);
			  locationInfo.setTime(7);
			  */
			  coachLoginBuffer.writeBytes(locationInfo.getBytes());
			  
			  
			  
		  }
		  catch (Exception e) {
			// TODO: handle exception
			  
		  }
		  byte[] coachLoginBytes = new byte[coachLoginBuffer.readableBytes()];
		  coachLoginBuffer.readBytes(coachLoginBytes);
		  
		  return coachLoginBytes;
	}
	
	public LoginCoach(byte[] bytes)
	{
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);
		
		byte[] coachNumBytes = new byte[16];
		hbuf.readBytes(coachNumBytes);
		byte[] coachIdentifierNoBytes = new byte[18];
		hbuf.readBytes(coachIdentifierNoBytes);
		
		byte[] teachCarTypeBytes = new byte[2];
		hbuf.readBytes(teachCarTypeBytes);
		
		byte[] loactionBytes = new byte[bytes.length - 36];
		hbuf.readBytes(loactionBytes);
		
		
		try {
			coachNum = new String(coachNumBytes,"GBK");
			coachIdentifierNo = new String(coachIdentifierNoBytes,"GBK");
			teachCarType = new String(teachCarTypeBytes,"GBK");
			locationInfo = new LocationInfo(loactionBytes);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public LoginCoach()
	{
		
	}

	@Override
	public String toString() {
		return "LoginCoach [coachNum=" + coachNum + ", coachIdentifierNo=" + coachIdentifierNo + ", teachCarType="
				+ teachCarType + ", locationInfo=" + locationInfo.toString() + "]";
	}
	
}
