package com.bjxc.supervise.model;

import org.apache.commons.io.output.ThresholdingOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TransportMessageBody implements IBytes {
	
	private byte messageType;
	private ExtendMessageBody extendMessageBody;
	public byte getMessageType() {
		return messageType;
	}
	public void setMessageType(byte messageType) {
		this.messageType = messageType;
	}
	public ExtendMessageBody getExtendMessageBody() {
		return extendMessageBody;
	}
	public void setExtendMessageBody(ExtendMessageBody extendMessageBody) {
		this.extendMessageBody = extendMessageBody;
	}
	
	
	public byte[] getBytes()
	{
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeByte(messageType);
		hbuf.writeBytes(extendMessageBody.getBytes());
		
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	public TransportMessageBody()
	{
		
	}
	public TransportMessageBody(byte[] bytes)
	{
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		messageType = hbuf.readByte();
		
		byte[] extendBytes = new byte[bytes.length - 1];
		hbuf.readBytes(extendBytes);
		extendMessageBody =  new ExtendMessageBody(extendBytes);

	}

}
