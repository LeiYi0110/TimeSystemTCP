package com.bjxc.supervise.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;


public class Message implements Comparable{
	
	public static short maxLength = 1023;
	
	private byte first = 0x7e;
	
	private Header header = new Header();
	private byte[] body;
	private byte check = 0;
	
	private byte last = 0x7e;
	
	private String originalMessage;
	
	
	public String getOriginalMessage() {
		return originalMessage;
	}
	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	
	public String getBodyToString() throws UnsupportedEncodingException{
		if(body == null || body.length < 1){
			return null;
		}
		return new String(body,"GBK");
	}
	
	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
	public byte getCheck() {
		return check;
	}
	public void setCheck(byte check) {
		this.check = check;
	}
	
	public Message() {
		super();
	}
	
	public Message(byte[] bytes) {
		
		ByteBuf buf = Unpooled.buffer();
		buf.writeBytes(bytes);
		first = buf.readByte();
		header = new Header();
		header.setVersion(buf.readByte());
		
		header.setId(buf.readShort());
		short property = buf.readShort();
		this.setProerty(header,property);
		
		byte[] mobileBytes = new byte[8];
		buf.readBytes(mobileBytes);
		header.setMobile(HexUtils.bcd2Str(mobileBytes));
		header.setNumber(buf.readShort());
		header.setBackup(buf.readByte());
		if(header.isSubPackage()){
			header.setPackageSize(buf.readShort());
			header.setPackageIndex(buf.readShort());
		}
		body = new byte[buf.readableBytes()-2];
		buf.readBytes(body);
		check = buf.readByte();
		last = buf.readByte();
	}
	
	private void setProerty(Header header,short property){
		header.setProperty(property);
		short a = 1 << 13;
		//short a = 1 << 12;
		if((property & a) == a){
			//娑堟伅鏈夊垎鍖�
			//瑙ｉ噴娑堟伅鍖呭皝鐘堕」
			header.setSubPackage(true);
		}
		//鍔犲瘑鏂瑰紡
		//0x400 : 10000000000
		//0x1c00 : 1110000000000
		if((property & 0x400) == 0x400){
			//閲囩敤浜哛SA鍔犲瘑
			header.setEncrypt("RSA");
		}else if((property & 0x1c00) == 0){
			//娌℃湁缂栫爜
			header.setEncrypt(null);
		}
		//1023 111111111
		short length = (short)(property & 1023);
		//short length = (short)(property - a);
		header.setLength(length);
		
		
		
	}

	
	public ByteBuf toByteBuf(){
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(first);
		buf.writeByte(header.getVersion());
		buf.writeShort(header.getId());
		buf.writeShort(header.getProperty());
		//buf.writeLong(header.getMobile());
		buf.writeBytes(HexUtils.str2Bcd(header.getMobile()));
		buf.writeShort(header.getNumber());
		buf.writeByte(header.getBackup());
		if(header.isSubPackage()){
			//buf.writeByte(header.getPackaging());
		}
		buf.writeBytes(this.body);
		buf.writeByte(this.check);
		
		buf.writeByte(last);
		/**
		byte[] a = null;
		for(int i = 0 ; i < a.length ; i++){
			if(a[i] == 0x7e){
				
			}
		}
		**/
		return buf;
	}
	
	public byte[] getBytes() {
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(first);
		buf.writeByte(header.getVersion());
		buf.writeShort(header.getId());
		buf.writeShort(header.getProperty());
		buf.writeBytes(HexUtils.str2Bcd(header.getMobile()));
		buf.writeShort(header.getNumber());
		buf.writeByte(header.getBackup());
		if(header.isSubPackage()){
			buf.writeByte(header.getPackageSize());
			buf.writeByte(header.getPackageIndex());
		}
		buf.writeBytes(this.body);
		buf.writeByte(this.check);
		
		buf.writeByte(last);
		
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		return bytes;
	}

	public byte getFirst() {
		return first;
	}

	public byte getLast() {
		return last;
	}
	
	public int compareTo(Object object) { 
		if(object instanceof Message){ 
			Header header=((Message)object).getHeader(); 
		    if(this.getHeader().getPackageIndex() > header.getPackageIndex()){ 
		        return 1; 
		    } 
		    else{ 
		    	return 0; 
		    } 
		} 
		return -1; 
	} 

}
