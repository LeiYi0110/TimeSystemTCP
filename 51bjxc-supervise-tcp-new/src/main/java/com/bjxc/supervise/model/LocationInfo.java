package com.bjxc.supervise.model;

import com.bjxc.supervise.netty.HexUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class LocationInfo implements IBytes{
	
	private int alertInfo;
	private int status;
	private int latitude;
	private int longtitude;
	
	private short carSpeed;
	
	private short gpsSpeed;
	
	private short orientation;
	
	private String time; //BCD[6]���ʾʱ��
	private List<ParamBS> paramList;

	public byte[] getBytes()
	{
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeInt(alertInfo);
		hbuf.writeInt(status);
		hbuf.writeInt(latitude);
		hbuf.writeInt(longtitude);
		hbuf.writeShort(carSpeed);
		hbuf.writeShort(gpsSpeed);
		hbuf.writeShort(orientation);
		hbuf.writeBytes(HexUtils.str2Bcd(time));
		if(paramList!=null){
			for (ParamBS param : paramList) {
				hbuf.writeBytes(param.getBytes());
			}
		}
		
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}

	public LocationInfo(byte[] bytes)
	{
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		alertInfo = hbuf.readInt();
		status =  hbuf.readInt();
		latitude = hbuf.readInt();
		longtitude = hbuf.readInt();
		carSpeed = hbuf.readShort();
		gpsSpeed = hbuf.readShort();
		orientation = hbuf.readShort();
		byte[] timeBytes = new byte[6];
		hbuf.readBytes(timeBytes);
		time = HexUtils.bcd2Str(timeBytes);
		System.out.println("time:"+time);
		paramList = new ArrayList<ParamBS>();
		while(hbuf.readableBytes()>0){
			byte paramId = hbuf.readByte();
			byte paramLen = hbuf.readByte();
			byte[] paramBytes = new byte[paramLen];
			hbuf.readBytes(paramBytes);

			ParamBS paramBS = new ParamBS(paramId,paramLen,paramBytes);
			paramList.add(paramBS);
		}
	}
	
	public int getAlertInfo() {
		return alertInfo;
	}

	public void setAlertInfo(int alertInfo) {
		this.alertInfo = alertInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(int longtitude) {
		this.longtitude = longtitude;
	}

	public short getCarSpeed() {
		return carSpeed;
	}

	public void setCarSpeed(short carSpeed) {
		this.carSpeed = carSpeed;
	}

	public short getGpsSpeed() {
		return gpsSpeed;
	}

	public void setGpsSpeed(short gpsSpeed) {
		this.gpsSpeed = gpsSpeed;
	}

	public short getOrientation() {
		return orientation;
	}

	public void setOrientation(short orientation) {
		this.orientation = orientation;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<ParamBS> getParamList() {
		return paramList;
	}

	public void setParamList(List<ParamBS> paramList) {
		this.paramList = paramList;
	}

	public LocationInfo()
	{
		
	}

	@Override
	public String toString() {
		return "LocationInfo [alertInfo=" + alertInfo + ", status=" + status + ", latitude=" + latitude
				+ ", longtitude=" + longtitude + ", carSpeed=" + carSpeed + ", gpsSpeed=" + gpsSpeed + ", orientation="
				+ orientation + ", time=" + time + ", paramList=" + paramList + "]";
	}

}
