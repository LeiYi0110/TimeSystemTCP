package com.bjxc.supervise.model;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResendPackage extends TransportObject {

	private short originalSerialNum;
	private byte resendPackageNumber;
	private List<Short>	paramList;
	
	public ResendPackage() {
		super();
		paramList = new ArrayList<Short>();
	}
	
	public ResendPackage(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		originalSerialNum = copiedBuffer.readShort();
		resendPackageNumber = copiedBuffer.readByte();
		paramList = new ArrayList<Short>();
		while(copiedBuffer.readableBytes()>0){
			short readShort = copiedBuffer.readShort();
			paramList.add(readShort);
		}
	}

	public byte[] getBytes() {
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeShort(originalSerialNum);
		if(paramList!=null){
			hbuf.writeByte(paramList.size());
			for (Short short1 : paramList) {
				hbuf.writeShort(short1);
			}
		}
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}

	public short getOriginalSerialNum() {
		return originalSerialNum;
	}

	public void setOriginalSerialNum(short originalSerialNum) {
		this.originalSerialNum = originalSerialNum;
	}

	public byte getResendPackageNumber() {
		return resendPackageNumber;
	}

	public void setResendPackageNumber(byte resendPackageNumber) {
		this.resendPackageNumber = resendPackageNumber;
	}

	public List<Short> getParamList() {
		return paramList;
	}

	public void setParamList(List<Short> paramList) {
		this.paramList = paramList;
	}

}
