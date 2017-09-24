package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 消息ID：0x8105。
 * 终端控制消息体数据格式见表B.26。
 * @author dev
 *
 */
public class TerminalOrder extends TransportObject {
	
	private byte orderCode;	//命令字 BYTE 终端控制命令字说明见表B.27
	private String order;	//命令参数 STRING 命令参数格式具体见后描述，每个字段之间采用”;”分隔，每个STRING字段先按GBK编码处理后再组成消息

	public TerminalOrder() {
		super();
	}
	
	public TerminalOrder(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		orderCode = copiedBuffer.readByte();
		if(copiedBuffer.readableBytes()>0){
			byte[] orderBytes = new byte[copiedBuffer.readableBytes()];
			copiedBuffer.readBytes(orderBytes);
			try {
				order = new String(orderBytes,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(orderCode);
		if(order!=null){
			try {
				buffer.writeBytes(order.getBytes("GBK"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public byte getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(byte orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
