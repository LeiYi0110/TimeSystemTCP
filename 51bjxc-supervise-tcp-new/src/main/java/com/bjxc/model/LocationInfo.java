package com.bjxc.model;

import com.bjxc.supervise.model.TransportObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class LocationInfo extends TransportObject{
	private Integer id;
	private Integer alertInfo;
	private Integer status;
	private Integer latitude;
	private Integer longtitude;
	private Integer carSpeed;
	private Integer gpsSpeed;
	private Integer orientation;
	private String time;
	private Integer sumDistance;
	private Integer gasonlineCost;
	private Integer elevation;
	private Integer engineSpeed;
	private Integer trainingrecordid;
	private Integer deviceId;
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAlertInfo() {
		return alertInfo;
	}
	public void setAlertInfo(Integer alertInfo) {
		this.alertInfo = alertInfo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLatitude() {
		return latitude;
	}
	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}
	public Integer getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(Integer longtitude) {
		this.longtitude = longtitude;
	}
	public Integer getCarSpeed() {
		if(carSpeed == null){
			return 0;
		}
		return carSpeed;
	}
	public void setCarSpeed(Integer carSpeed) {
		this.carSpeed = carSpeed;
	}
	public Integer getGpsSpeed() {
		if(gpsSpeed == null){
			return 0;
		}
		return gpsSpeed;
	}
	public void setGpsSpeed(Integer gpsSpeed) {
		this.gpsSpeed = gpsSpeed;
	}
	public Integer getOrientation() {
		if(orientation == null){
			return 0;
		}
		return orientation;
	}
	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getSum_distance() {
		if(sumDistance == null){
			return 0;
		}
		return sumDistance;
	}
	public void setSum_distance(Integer sum_distance) {
		this.sumDistance = sum_distance;
	}
	public Integer getGasonline_cost() {
		if(gasonlineCost == null){
			return 0;
		}
		return gasonlineCost;
	}
	public void setGasonline_cost(Integer gasonline_cost) {
		this.gasonlineCost = gasonline_cost;
	}
	public Integer getElevation() {
		if(elevation == null){
			return 0;
		}
		return elevation;
	}
	public void setElevation(Integer elevation) {
		this.elevation = elevation;
	}
	public Integer getEngine_speed() {
		if(engineSpeed == null){
			return 0;
		}
		return engineSpeed;
	}
	public void setEngine_speed(Integer engine_speed) {
		this.engineSpeed = engine_speed;
	}
	public Integer getTrainingrecordid() {
		return trainingrecordid;
	}
	public void setTrainingrecordid(Integer trainingrecordid) {
		this.trainingrecordid = trainingrecordid;
	}
	
	@Override
	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		writeByteBuf(buffer,id);
		writeByteBuf(buffer,alertInfo);
		writeByteBuf(buffer,status);
		writeByteBuf(buffer,latitude);
		writeByteBuf(buffer,longtitude);
		writeByteBuf(buffer,carSpeed);
		writeByteBuf(buffer,gpsSpeed);
		writeByteBuf(buffer,orientation);
		if(time == null){
			buffer.writeBytes("000000000000".getBytes());
		}else{
			buffer.writeBytes(time.getBytes());
		}
		writeByteBuf(buffer,sumDistance);
		writeByteBuf(buffer,gasonlineCost);
		writeByteBuf(buffer,elevation);
		writeByteBuf(buffer,engineSpeed);
		writeByteBuf(buffer,trainingrecordid);
		writeByteBuf(buffer,deviceId);
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	ByteBuf writeByteBuf(ByteBuf byteBuf,Integer i){
		if (i == null){
			byteBuf.writeInt(0);
		}else{
			byteBuf.writeInt(i);
		}
		return byteBuf;
	}
	
	public LocationInfo() {
		super();
	}
	
	public LocationInfo(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		id = copiedBuffer.readInt();
		alertInfo = copiedBuffer.readInt();
		status = copiedBuffer.readInt();
		latitude = copiedBuffer.readInt();
		longtitude = copiedBuffer.readInt();
		carSpeed = copiedBuffer.readInt();
		gpsSpeed = copiedBuffer.readInt();
		orientation = copiedBuffer.readInt();
		byte[] timeBytes = new byte[12];
		copiedBuffer.readBytes(timeBytes);
		sumDistance = copiedBuffer.readInt();
		gasonlineCost = copiedBuffer.readInt();
		elevation = copiedBuffer.readInt();
		engineSpeed = copiedBuffer.readInt();
		trainingrecordid = copiedBuffer.readInt();
		deviceId = copiedBuffer.readInt();
		try {
			time = new String(timeBytes,"GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
