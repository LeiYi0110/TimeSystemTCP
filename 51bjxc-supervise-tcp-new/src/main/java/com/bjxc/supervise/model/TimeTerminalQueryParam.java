package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 查询计时终端应用参数应答
 * 透传消息ID：0x0503
 * 计时终端使用此消息回复计时平台下发的查询计时终端应用参数消息，查询计时终端应用参数消息数据格式见表B.60。
 * @author dev
 *
 */
public class TimeTerminalQueryParam extends TransportObject {
	
	/**
	0：成功；
	1：失败 */
	private byte resultCode;	//查询结果 BYTE
	/**
	定时拍照时间间隔 BYTE 单位：min，默认值15。 在学员登录后间隔固定时间拍摄照片。 */
	private byte getPhotoInterval;
	/**
	照片上传设置
	BYTE
	0：不自动请求上传；
	1：自动请求上传 */
	private byte uploadSetting;
	/** 
	是否报读附加消息
	BYTE
	1：自动报读；2：不报读。
	控制是否报读消息中的附加消息，如果下行消息中指定了是否报读，则遵循该消息的设置执行。 */
	private byte hasExtalMsg;
	/**
	熄火后停止学时计时的延时时间
	BYTE
	单位：min */
	private byte stopStudingTimeDelay;
	/**
	熄火后GNSS数据包上传间隔
	WORD
	单位：s，默认值3600，0表示不上传 */
	private short GNSSInterval;
	/**
	熄火后教练自动登出的延时时间
	WORD
	单位：min，默认值150 */
	private short autoLogoutDelay;
	/**
	重新验证身份时间
	WORD
	单位：min，默认值30 */
	private short reValidate;
	/**
	教练跨校教学
	BYTE
	1：允许
	2：禁止 */
	private byte enableCrossSchoolTeaching;
	/**
	学员跨校学习
	BYTE
	1：允许
	2：禁止 */
	private byte enableCrossSchoolStuding;
	/**
	响应平台同类消息时间间隔
	WORD
	单位：s，在该时间间隔内对平台发送的多次相同ID消息可拒绝执行回复失败 */
	private short answerInterval;

	public TimeTerminalQueryParam() {
		super();
	}
	
	public TimeTerminalQueryParam(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
		getPhotoInterval = copiedBuffer.readByte();
		uploadSetting = copiedBuffer.readByte();
		hasExtalMsg = copiedBuffer.readByte();
		stopStudingTimeDelay = copiedBuffer.readByte();
		GNSSInterval = copiedBuffer.readShort();
		autoLogoutDelay = copiedBuffer.readShort();
		reValidate = copiedBuffer.readShort();
		enableCrossSchoolTeaching = copiedBuffer.readByte();
		enableCrossSchoolStuding = copiedBuffer.readByte();
		answerInterval = copiedBuffer.readShort();
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(resultCode);
		buffer.writeByte(getPhotoInterval);
		buffer.writeByte(uploadSetting);
		buffer.writeByte(hasExtalMsg);
		buffer.writeByte(stopStudingTimeDelay);
		buffer.writeShort(GNSSInterval);
		buffer.writeShort(autoLogoutDelay);
		buffer.writeShort(reValidate);
		buffer.writeByte(enableCrossSchoolTeaching);
		buffer.writeByte(enableCrossSchoolStuding);
		buffer.writeShort(answerInterval);
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public byte getParamId() {
		return resultCode;
	}

	public void setParamId(byte resultCode) {
		this.resultCode = resultCode;
	}

	public byte getGetPhotoInterval() {
		return getPhotoInterval;
	}

	public void setGetPhotoInterval(byte getPhotoInterval) {
		this.getPhotoInterval = getPhotoInterval;
	}

	public byte getUploadSetting() {
		return uploadSetting;
	}

	public void setUploadSetting(byte uploadSetting) {
		this.uploadSetting = uploadSetting;
	}

	public byte getHasExtalMsg() {
		return hasExtalMsg;
	}

	public void setHasExtalMsg(byte hasExtalMsg) {
		this.hasExtalMsg = hasExtalMsg;
	}

	public byte getStopStudingTimeDelay() {
		return stopStudingTimeDelay;
	}

	public void setStopStudingTimeDelay(byte stopStudingTimeDelay) {
		this.stopStudingTimeDelay = stopStudingTimeDelay;
	}

	public short getGNSSInterval() {
		return GNSSInterval;
	}

	public void setGNSSInterval(short gNSSInterval) {
		GNSSInterval = gNSSInterval;
	}

	public short getAutoLogoutDelay() {
		return autoLogoutDelay;
	}

	public void setAutoLogoutDelay(short autoLogoutDelay) {
		this.autoLogoutDelay = autoLogoutDelay;
	}

	public short getReValidate() {
		return reValidate;
	}

	public void setReValidate(short reValidate) {
		this.reValidate = reValidate;
	}

	public byte getEnableCrossSchoolTeaching() {
		return enableCrossSchoolTeaching;
	}

	public void setEnableCrossSchoolTeaching(byte enableCrossSchoolTeaching) {
		this.enableCrossSchoolTeaching = enableCrossSchoolTeaching;
	}

	public byte getEnableCrossSchoolStuding() {
		return enableCrossSchoolStuding;
	}

	public void setEnableCrossSchoolStuding(byte enableCrossSchoolStuding) {
		this.enableCrossSchoolStuding = enableCrossSchoolStuding;
	}

	public short getAnswerInterval() {
		return answerInterval;
	}

	public void setAnswerInterval(short answerInterval) {
		this.answerInterval = answerInterval;
	}

	@Override
	public String toString() {
		return "TimeTerminalQueryParam [resultCode=" + resultCode + ", getPhotoInterval=" + getPhotoInterval
				+ ", uploadSetting=" + uploadSetting + ", hasExtalMsg=" + hasExtalMsg + ", stopStudingTimeDelay="
				+ stopStudingTimeDelay + ", GNSSInterval=" + GNSSInterval + ", autoLogoutDelay=" + autoLogoutDelay
				+ ", reValidate=" + reValidate + ", enableCrossSchoolTeaching=" + enableCrossSchoolTeaching
				+ ", enableCrossSchoolStuding=" + enableCrossSchoolStuding + ", answerInterval=" + answerInterval + "]";
	}

}
