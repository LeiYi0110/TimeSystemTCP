package com.bjxc.jtt.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class JttLogin extends IBytes {
	
	private int userid;
	private String password;
	private String down_link_ip;
	private short down_link_port;
	
	public JttLogin() {
		super();
	}
	public JttLogin(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		userid = copiedBuffer.readInt();
		byte[] passwordBytes = new byte[8];
		copiedBuffer.readBytes(passwordBytes);
		byte[] down_link_ipBytes = new byte[32];
		copiedBuffer.readBytes(down_link_ipBytes);
		try {
			password = new String(passwordBytes,"GBK");
			down_link_ip = new String(down_link_ipBytes,"GBK");
			down_link_port = copiedBuffer.readShort();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] getBytes() {
		ByteBuf hbuf = Unpooled.buffer();
		
		try {
			hbuf.writeInt(userid);
			hbuf.writeBytes(password.getBytes("GBK"));
			hbuf.writeBytes(down_link_ip.getBytes("GBK"));
			hbuf.writeShort(down_link_port);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDown_link_ip() {
		return down_link_ip;
	}
	public void setDown_link_ip(String down_link_ip) {
		this.down_link_ip = down_link_ip;
	}
	public short getDown_link_port() {
		return down_link_port;
	}
	public void setDown_link_port(short down_link_port) {
		this.down_link_port = down_link_port;
	}

}
