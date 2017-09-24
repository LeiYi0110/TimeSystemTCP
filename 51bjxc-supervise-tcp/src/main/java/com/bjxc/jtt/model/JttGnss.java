package com.bjxc.jtt.model;

import java.util.ArrayList;

import com.bjxc.supervise.netty.HexUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class JttGnss extends IBytes{
	
	private byte ENCRTYPT;	//该字段标识传输的定位信息是否使用国家测绘局批准的地图保 密插件进行加密。加密标识：1-已加密，0-未加密
	private byte[] DATE;	//BCD[4]yyyyMMdd, 曰月年（dmyy），年的表示是先将年转换成两位十六进制数，如 2009 表示为 0x07 0x09
	private byte[] TIME;	//BCD[3],HHmmss, 时分秒(！“^)
	private int LON;	//经度，单位为1 *10~6度
	private int LAT;	//经度，单位为1 *10~6度
	private short VEC1;	//速度，指卫星定位车载终端设备上传的行车速度信息，为必填项， 单位为千米每小时(km/h) ^
	private short VEC2;	//行驶记录速度，指车辆行驶记录设备上传的行车速度信息，单位为千米每小时(km/h)
	private int VEC3;	//车辆当前总里程数,指车辆上传的行车里程数，单位为千米(km/h)
	private short DIRECTION;	//方向，0〜359，单位为度（。），正北为0，顺时针
	private short ALTTTUDE;	//海拔高度，单位为米（m）
	private int STATE;	//车辆状态，二进制表示：B31B30……B2B1B0。具体定义按照JT/T 808—2011中表17的规定
	private int ALARM;	//报警状态，二进制表示，0表示正常，1表示报警: B31B30B29……B2B1B0。具体定义按照T/T 808—2011中表18的规定
	
	public JttGnss() {
		super();
	}

	public JttGnss(byte[] bytes) {
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		ENCRTYPT = hbuf.readByte();
		DATE = new byte[4];
		hbuf.readBytes(DATE);
		TIME = new byte[3];
		hbuf.readBytes(TIME);
		LON =  hbuf.readInt();
		LAT = hbuf.readInt();
		VEC1 = hbuf.readShort();
		VEC2 = hbuf.readShort();
		VEC3 = hbuf.readInt();
		DIRECTION = hbuf.readShort();
		ALTTTUDE = hbuf.readShort();
		STATE = hbuf.readInt();
		ALARM = hbuf.readInt();
	}

	@Override
	public byte[] getBytes() {
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeByte(ENCRTYPT);
		hbuf.writeBytes(DATE);
		hbuf.writeBytes(TIME);
		hbuf.writeInt(LON);
		hbuf.writeInt(LAT);
		hbuf.writeShort(VEC1);
		hbuf.writeShort(VEC2);
		hbuf.writeInt(VEC3);
		hbuf.writeShort(DIRECTION);
		hbuf.writeShort(ALTTTUDE);
		hbuf.writeInt(STATE);
		hbuf.writeInt(ALARM);
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	public byte getENCRTYPT() {
		return ENCRTYPT;
	}
	public void setENCRTYPT(byte eNCRTYPT) {
		ENCRTYPT = eNCRTYPT;
	}
	public byte[] getDATE() {
		return DATE;
	}

	public void setDATE(byte[] dATE) {
		DATE = dATE;
	}

	public byte[] getTIME() {
		return TIME;
	}

	public void setTIME(byte[] tIME) {
		TIME = tIME;
	}

	public int getLON() {
		return LON;
	}
	public void setLON(int lON) {
		LON = lON;
	}
	public int getLAT() {
		return LAT;
	}
	public void setLAT(int lAT) {
		LAT = lAT;
	}
	public short getVEC1() {
		return VEC1;
	}
	public void setVEC1(short vEC1) {
		VEC1 = vEC1;
	}
	public short getVEC2() {
		return VEC2;
	}
	public void setVEC2(short vEC2) {
		VEC2 = vEC2;
	}
	public int getVEC3() {
		return VEC3;
	}
	public void setVEC3(int vEC3) {
		VEC3 = vEC3;
	}
	public short getDIRECTION() {
		return DIRECTION;
	}
	public void setDIRECTION(short dIRECTION) {
		DIRECTION = dIRECTION;
	}
	public short getALTTTUDE() {
		return ALTTTUDE;
	}
	public void setALTTTUDE(short aLTTTUDE) {
		ALTTTUDE = aLTTTUDE;
	}
	public int getSTATE() {
		return STATE;
	}
	public void setSTATE(int sTATE) {
		STATE = sTATE;
	}
	public int getALARM() {
		return ALARM;
	}
	public void setALARM(int aLARM) {
		ALARM = aLARM;
	}
}
