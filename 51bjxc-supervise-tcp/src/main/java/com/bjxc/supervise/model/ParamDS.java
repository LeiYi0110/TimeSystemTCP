package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ParamDS extends TransportObject {
	
	private int paramId;	//参数ID DWORD 参数ID定义及说明表目，具体定义见表B.17
	private byte paramLen;	//参数长度 BYTE
	private Object param;	//参数值	STRING

	public ParamDS() {
		super();
	}
	
	public ParamDS(int paramId, byte paramLen, byte[] paramBytes) {
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

	public ParamDS(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		paramId = copiedBuffer.readInt();
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
		buffer.writeInt(paramId);
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

	public void setParamId(int paramId) {
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
		String string = "paramDS-->id: "+paramId+" len: "+paramLen+" param: "+param;
		return string;
	}

}
