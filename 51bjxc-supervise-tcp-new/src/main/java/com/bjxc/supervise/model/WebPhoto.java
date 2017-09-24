package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class WebPhoto extends TransportObject{
	
	private String lic;	//车牌号, 2个汉字5个基本字母 9位
	private String photoUrl;	//图片链接,长度不定

	public WebPhoto() {
		super();
	}
	
	public WebPhoto(byte[] bytes) {
		ByteBuf buffer = Unpooled.copiedBuffer(bytes);
		byte[] licBytes = new byte[9];
		buffer.readBytes(licBytes);
		byte[] photoUrlBytes = new byte[buffer.readableBytes()];
		buffer.readBytes(photoUrlBytes);
		try {
			lic = new String(licBytes,"GBK");
			photoUrl = new String(photoUrlBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(lic.getBytes("GBK"));
			buffer.writeBytes(photoUrl.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public String getLic() {
		return lic;
	}

	public void setLic(String lic) {
		this.lic = lic;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
