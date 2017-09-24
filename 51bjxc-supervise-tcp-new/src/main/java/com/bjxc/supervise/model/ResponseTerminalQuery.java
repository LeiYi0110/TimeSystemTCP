package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 查询终端参数应答消息体数据格式
 * 
 * 消息ID：0x0104。
 * 查询终端参数应答消息体数据格式见表B.19。
 * @author dev
 *
 */
public class ResponseTerminalQuery extends TransportObject {
	
	private short serialNum;	//应答流水号	WORD
	private byte paramNum;	//应答参数个数 byte
	private byte paramNumber;	//包参数个数
	private List<ParamDS> paramList;	//包参数个数 参数项格式和定义见表B.16

	public ResponseTerminalQuery() {
		super();
	}
	
	public ResponseTerminalQuery(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		serialNum = copiedBuffer.readShort();
		paramNum = copiedBuffer.readByte();
		paramNumber = copiedBuffer.readByte();
		paramList = new ArrayList<ParamDS>();
		while(copiedBuffer.readableBytes()>0){
			int id = copiedBuffer.readInt();
			byte len = copiedBuffer.readByte();
			byte[] paramBytes = new byte[len];
			copiedBuffer.readBytes(paramBytes);
			ParamDS paramDS = new ParamDS(id,len,paramBytes);
			paramList.add(paramDS);
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		
		buffer.writeShort(serialNum);
		buffer.writeByte(paramNum);
		buffer.writeByte(paramNumber);
		for (ParamDS paramDS : paramList) {
			buffer.writeBytes(paramDS.getBytes());
		}
		
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public short getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(short serialNum) {
		this.serialNum = serialNum;
	}

	public byte getParamNum() {
		return paramNum;
	}

	public void setParamNum(byte paramNum) {
		this.paramNum = paramNum;
	}

	public byte getParamNumber() {
		return paramNumber;
	}

	public void setParamNumber(byte paramNumber) {
		this.paramNumber = paramNumber;
	}

	public List<ParamDS> getParamList() {
		return paramList;
	}

	public void setParamList(List<ParamDS> paramList) {
		this.paramList = paramList;
	}

}
