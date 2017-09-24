package com.bjxc.jtt.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class JttPhoto extends IBytes{
	private byte PHOTO_RSP_FLAG;	//拍照应答标识，标识拍照后的结果或原因，定义如下0x00：不支持拍照相0x01：完成拍照0x02：完成拍照，照片数据稍后传递0x03：未拍照（不在线）0x04：未拍照（无法使用指定服务）0x05：未拍照（其他原因）0x09：车牌号码错误
	private JttGnss GBSS_DATA;	//拍照位置地点，详见4.5.8.1
	private byte LENS_ID;	//锁头ID
	private int PHOTO_LEN;	//图片长度
	private byte SIZE_TYPE;	//图片大小
	private byte TYPE;	//图像格式，定义如下：0x01：jpg0x02：gif0x03：uif0x04：png
	private byte[] PHOTO;	//图片内容
	
	public JttPhoto() {
		super();
	}

	public JttPhoto(byte[] bytes) {
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		PHOTO_RSP_FLAG =  hbuf.readByte();
		byte[] dateBytes = new byte[36];
		hbuf.readBytes(dateBytes);
		GBSS_DATA = new JttGnss(dateBytes);
		LENS_ID =  hbuf.readByte();
		PHOTO_LEN = hbuf.readInt();
		SIZE_TYPE =  hbuf.readByte();
		TYPE =  hbuf.readByte();
		PHOTO = new byte[hbuf.readableBytes()];
		hbuf.readBytes(PHOTO);
	}
	
	@Override
	public byte[] getBytes(){
		ByteBuf hbuf = Unpooled.buffer();
		
		hbuf.writeByte(PHOTO_RSP_FLAG);
		hbuf.writeByte(LENS_ID);
		hbuf.writeInt(PHOTO_LEN);
		hbuf.writeByte(SIZE_TYPE);
		hbuf.writeByte(TYPE);
		hbuf.writeBytes(PHOTO);
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	public byte getPHOTO_RSP_FLAG() {
		return PHOTO_RSP_FLAG;
	}
	public void setPHOTO_RSP_FLAG(byte pHOTO_RSP_FLAG) {
		PHOTO_RSP_FLAG = pHOTO_RSP_FLAG;
	}
	public JttGnss getGBSS_DATA() {
		return GBSS_DATA;
	}
	public void setGBSS_DATA(JttGnss gBSS_DATA) {
		GBSS_DATA = gBSS_DATA;
	}
	public byte getLENS_ID() {
		return LENS_ID;
	}
	public void setLENS_ID(byte lENS_ID) {
		LENS_ID = lENS_ID;
	}
	public int getPHOTO_LEN() {
		return PHOTO_LEN;
	}
	public void setPHOTO_LEN(int pHOTO_LEN) {
		PHOTO_LEN = pHOTO_LEN;
	}
	public byte getSIZE_TYPE() {
		return SIZE_TYPE;
	}
	public void setSIZE_TYPE(byte sIZE_TYPE) {
		SIZE_TYPE = sIZE_TYPE;
	}
	public byte getTYPE() {
		return TYPE;
	}
	public void setTYPE(byte tYPE) {
		TYPE = tYPE;
	}
	public byte[] getPHOTO() {
		return PHOTO;
	}
	public void setPHOTO(byte[] pHOTO) {
		PHOTO = pHOTO;
	}
}
