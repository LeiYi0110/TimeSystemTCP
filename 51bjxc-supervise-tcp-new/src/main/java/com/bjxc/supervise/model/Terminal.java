package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 终端参数消息体数据格式
 * @author dev
 *
 */
public class Terminal extends TransportObject {
	
	private byte parameterNumber;	//参数总数
	private byte parameterCount;	//分包参数个数
	private List<ParamDS> paramList;	//参数项列表 参数项格式见表B.16

	public Terminal() {
		super();
	}
	
	public Terminal(byte[] bytes) {
		try{
		ByteBuf buffer = Unpooled.copiedBuffer(bytes);
		parameterNumber = buffer.readByte();
		parameterCount = buffer.readByte();
		paramList = new ArrayList<ParamDS>();
		while(buffer.readableBytes()>0){
			int paramId = buffer.readInt();
			byte paramLen = buffer.readByte();
			byte[] paramBytes = new byte[paramLen];
			buffer.readBytes(paramBytes);
			
			ParamDS paramDS = new ParamDS();
			paramDS.setParamId(paramId);
			paramDS.setParamLen(paramLen);
			paramDS.setParam(paramBytes);
			paramList.add(paramDS);
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		try{
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(parameterNumber);
		buffer.writeByte(parameterCount);
		for (ParamDS param : paramList) {
			buffer.writeBytes(param.getBytes());
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public byte getParameterNumber() {
		return parameterNumber;
	}

	public void setParameterNumber(byte parameterNumber) {
		this.parameterNumber = parameterNumber;
	}

	public byte getParameterCount() {
		return parameterCount;
	}

	public void setParameterCount(byte parameterCount) {
		this.parameterCount = parameterCount;
	}

	public List<ParamDS> getParamList() {
		return paramList;
	}

	public void setParamList(List<ParamDS> paramList) {
		this.paramList = paramList;
	}

}
