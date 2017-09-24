package com.bjxc.jtt.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class JttWarn extends IBytes{
	
	private byte WARN_SRC;	//报警信息来源，定义如下：0x01：车载终端；0x02：企业监控平台；0x03：政府监管平台； 0x09：其他
	private short WARN_TYPE;	//报警类型，详见表75
	private long WARN_TIME;	//报警时间，UTC时间格式
	private int INFO_ID;	//信息ID
	private int INFO_LENGTH;	//报警数据长度，最长1024字节
	private String INFO_CONTENT;	//上报报警信息内容
	
	public JttWarn() {
		super();
	}

	public JttWarn(byte[] bytes) {
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		WARN_SRC =  hbuf.readByte();
		WARN_TYPE =  hbuf.readShort();
		WARN_TIME = hbuf.readLong();
		INFO_ID = hbuf.readInt();
		INFO_LENGTH = hbuf.readInt();
		byte[] info_contentBytes = new byte[8];
		hbuf.readBytes(info_contentBytes);
		try {
			INFO_CONTENT = new String(info_contentBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] getBytes(){
		ByteBuf hbuf = Unpooled.buffer();
		
		hbuf.writeByte(WARN_SRC);
		hbuf.writeShort(WARN_TYPE);
		hbuf.writeLong(WARN_TIME);
		hbuf.writeInt(INFO_ID);
		hbuf.writeInt(INFO_LENGTH);
		try {
			hbuf.writeBytes(INFO_CONTENT.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	public byte getWARN_SRC() {
		return WARN_SRC;
	}
	public void setWARN_SRC(byte wARN_SRC) {
		WARN_SRC = wARN_SRC;
	}
	public short getWARN_TYPE() {
		return WARN_TYPE;
	}
	public void setWARN_TYPE(short wARN_TYPE) {
		WARN_TYPE = wARN_TYPE;
	}
	public long getWARN_TIME() {
		return WARN_TIME;
	}

	public void setWARN_TIME(long wARN_TIME) {
		WARN_TIME = wARN_TIME;
	}

	public int getINFO_ID() {
		return INFO_ID;
	}
	public void setINFO_ID(int iNFO_ID) {
		INFO_ID = iNFO_ID;
	}
	public int getINFO_LENGTH() {
		return INFO_LENGTH;
	}
	public void setINFO_LENGTH(int iNFO_LENGTH) {
		INFO_LENGTH = iNFO_LENGTH;
	}
	public String getINFO_CONTENT() {
		return INFO_CONTENT;
	}
	public void setINFO_CONTENT(String iNFO_CONTENT) {
		INFO_CONTENT = iNFO_CONTENT;
	}
}
