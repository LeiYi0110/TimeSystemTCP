package com.bjxc.jtt.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class JttBase extends IBytes{
	
	private String VEHICLE_NO ;	//车牌号,21位
	private byte VEHICLE_COLOR;	//车牌颜色,按照JT/T 415 — 2006中5.4.12的规定
	private short DATA_TYPE;	//子业务类型标识
	private int DATA_LENGTH;	//后续数据长度
	private IBytes DATA;//36字节,详见 4.5.8.1  
	
	public JttBase() {
		super();
	}

	public JttBase(byte[] bytes) {
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		byte[] dateBytes = new byte[21];
		hbuf.readBytes(dateBytes);
		try {
			VEHICLE_NO = new String(dateBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		VEHICLE_COLOR =  hbuf.readByte();
		DATA_TYPE = hbuf.readShort();
		DATA_LENGTH = hbuf.readInt();
		byte[] gnss_dataBytes = new byte[21];
		hbuf.readBytes(gnss_dataBytes);
		DATA = new IBytes(gnss_dataBytes);
	}

	@Override
	public byte[] getBytes() {
		ByteBuf hbuf = Unpooled.buffer();
		try {
			hbuf.writeBytes(VEHICLE_NO.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		hbuf.writeByte(VEHICLE_COLOR);
		hbuf.writeShort(DATA_TYPE);
		if(DATA != null){
			hbuf.writeInt(DATA.getBytes().length);
		}else{
			hbuf.writeInt(0);
		}
		hbuf.writeBytes(DATA.getBytes());
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	public String getVEHICLE_NO() {
		return VEHICLE_NO;
	}
	public void setVEHICLE_NO(String vEHICLE_NO) {
		VEHICLE_NO = vEHICLE_NO;
	}
	public byte getVEHICLE_COLOR() {
		return VEHICLE_COLOR;
	}
	public void setVEHICLE_COLOR(byte vEHICLE_COLOR) {
		VEHICLE_COLOR = vEHICLE_COLOR;
	}
	public short getDATA_TYPE() {
		return DATA_TYPE;
	}
	public void setDATA_TYPE(short dATA_TYPE) {
		DATA_TYPE = dATA_TYPE;
	}
	public int getDATA_LENGTH() {
		return DATA_LENGTH;
	}
	public void setDATA_LENGTH(int dATA_LENGTH) {
		DATA_LENGTH = dATA_LENGTH;
	}
	public IBytes getDATA() {
		return DATA;
	}
	public void setDATA(IBytes dATA) {
		DATA = dATA;
	}
}
