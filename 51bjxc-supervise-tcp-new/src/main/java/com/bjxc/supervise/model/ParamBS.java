package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ParamBS extends TransportObject {
	
	private byte paramId;	//参数ID DWORD 参数ID定义及说明表目，具体定义见表B.17
	private byte paramLen;	//参数长度 BYTE
	private Object param;	//参数值	STRING

	public ParamBS() {
		super();
	}

	public ParamBS(byte paramId, byte paramLen, Object param) {
		this.paramId = paramId;
		this.paramLen = paramLen;
		this.param = param;
	}

	public ParamBS(byte paramId, byte paramLen, byte[] paramBytes) {
		this.paramId = paramId;
		this.paramLen = paramLen;
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(paramBytes);
		if(paramLen==1){
			param = copiedBuffer.readByte();
		}else if(paramLen==2){
			param = copiedBuffer.readShort();
		}else if(paramLen==4){
			param = copiedBuffer.readInt();
		}else{
			copiedBuffer.readBytes(paramBytes);
			try {
				param = new String(paramBytes,"GBK");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public ParamBS(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		paramId = copiedBuffer.readByte();
		paramLen = copiedBuffer.readByte();
		if(paramLen==1){
			param = copiedBuffer.readByte();
		}else if(paramLen==2){
			param = copiedBuffer.readShort();
		}else if(paramLen==4){
			param = copiedBuffer.readInt();
		}else{
			byte[] paramBytes = new byte[paramLen];
			copiedBuffer.readBytes(paramBytes);
			try {
				param = new String(paramBytes,"GBK");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(paramId);
		buffer.writeByte(paramLen);
		if(paramLen==1){
			buffer.writeByte((byte)param);
		}else if(paramLen==2){
			buffer.writeShort((short)param);
		}else if(paramLen==4){
			buffer.writeInt((int)param);
		}else{
			try {
				String string = (String)param;
				buffer.writeBytes(string.getBytes("GBK"));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public int getParamId() {
		return paramId;
	}

	public void setParamId(byte paramId) {
		this.paramId = paramId;
	}

	public byte getParamLen() {
		return paramLen;
	}

	public void setParamLen(byte paramLen) {
		this.paramLen = paramLen;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "ParamBS [paramId=" + paramId + ", paramLen=" + paramLen + ", param=" + param + "]";
	}

}
