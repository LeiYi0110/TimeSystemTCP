package com.bjxc.supervise.model;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 查询指定终端参数消息体数据格式
 * 
 * 消息ID：0x8106。
 * 查询指定终端参数消息体数据格式见表B.18，终端采用0x0104指令应答。
 * @author dev
 *
 */
public class TerminalQuery extends TransportObject {
	
	private byte paramNumber;	//参数总数 参数总数为n
	private List<Integer> paramList;	//参数ID列表 BYTE[4*n] 参数顺序排列，如“参数ID1参数ID2……参数IDn”

	public TerminalQuery() {
		super();
	}
	
	public TerminalQuery(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		paramList = new ArrayList<Integer>();
		paramNumber = copiedBuffer.readByte();
		while(copiedBuffer.readableBytes()>0){
			int readInt = copiedBuffer.readInt();
			paramList.add(readInt);
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();

		buffer.writeByte(paramNumber);
		for (Integer integer : paramList) {
			buffer.writeInt(integer);
		}

		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public byte getParamNumber() {
		return paramNumber;
	}

	public void setParamNumber(byte paramNumber) {
		this.paramNumber = paramNumber;
	}

	public List<Integer> getParamList() {
		return paramList;
	}

	public void setParamList(List<Integer> paramList) {
		this.paramList = paramList;
	}

}
