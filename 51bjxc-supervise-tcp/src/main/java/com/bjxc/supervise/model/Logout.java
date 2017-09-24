package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Logout  extends TransportObject  {
	private String num;
	private String password;
	
	
	public Logout() {
		super();
	}
	
	public Logout(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		byte[] numByte = new byte[5];
		copiedBuffer.readBytes(numByte);
		byte[] pswByte = new byte[8];
		copiedBuffer.readBytes(pswByte);
		try {
			num = new String(numByte,"GBK");
			password = new String(pswByte,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(num.getBytes("GBK"));
			buffer.writeBytes(password.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] bytes = new byte[buffer.readableBytes()];
		buffer.readBytes(bytes);
		  
		  return bytes;
	}
	
}
