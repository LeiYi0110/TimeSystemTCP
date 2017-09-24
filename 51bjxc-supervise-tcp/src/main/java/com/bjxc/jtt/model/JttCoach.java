package com.bjxc.jtt.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class JttCoach extends IBytes{
	
	private String DRIVER_NAME;	//驾驶员姓名
	private String DRIVER_ID;	//身份证编号
	private String LICENCE;	//从业资格证号(备用〉
	private String ORG_NAME;	//发证机构名称(备用〉
	
	public JttCoach(byte[] bytes) {
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		try {
			byte[] dateBytes = new byte[16];
			hbuf.readBytes(dateBytes);
			DRIVER_NAME = new String(dateBytes,"GBK");
			byte[] driver_idBytes = new byte[16];
			hbuf.readBytes(driver_idBytes);
			DRIVER_ID = new String(driver_idBytes,"GBK");
			byte[] licenceBytes = new byte[16];
			hbuf.readBytes(licenceBytes);
			LICENCE = new String(licenceBytes,"GBK");
			byte[] org_nameBytes = new byte[16];
			hbuf.readBytes(org_nameBytes);
			ORG_NAME = new String(org_nameBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public JttCoach() {
	}

	@Override
	public byte[] getBytes(){
		ByteBuf hbuf = Unpooled.buffer();
		
		try {
			hbuf.writeBytes(DRIVER_NAME.getBytes("GBK"));
			hbuf.writeBytes(DRIVER_ID.getBytes("GBK"));
			if(LICENCE != null){
				hbuf.writeBytes(LICENCE.getBytes("GBK"));
			}
			if(ORG_NAME != null){
				hbuf.writeBytes(ORG_NAME.getBytes("GBK"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	public String getDRIVER_NAME() {
		return DRIVER_NAME;
	}
	public void setDRIVER_NAME(String dRIVER_NAME) {
		DRIVER_NAME = dRIVER_NAME;
	}
	public String getDRIVER_ID() {
		return DRIVER_ID;
	}
	public void setDRIVER_ID(String dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}
	public String getLICENCE() {
		return LICENCE;
	}
	public void setLICENCE(String lICENCE) {
		LICENCE = lICENCE;
	}
	public String getORG_NAME() {
		return ORG_NAME;
	}
	public void setORG_NAME(String oRG_NAME) {
		ORG_NAME = oRG_NAME;
	}
}
