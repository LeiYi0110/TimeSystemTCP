package com.bjxc.jtt.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class IBytes {
	
	private byte[] bytes;

	public IBytes() {
		super();
	}

	public IBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes(){
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeBytes(bytes);
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	};

}
