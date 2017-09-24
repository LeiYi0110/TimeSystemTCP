package com.bjxc.supervise.netty;

public class GeneralResponse {
	
	private short seq;
	private short requestMessageId;
	private byte result;
	public short getSeq() {
		return seq;
	}
	public void setSeq(short seq) {
		this.seq = seq;
	}
	public short getRequestMessageId() {
		return requestMessageId;
	}
	public void setRequestMessageId(short requestMessageId) {
		this.requestMessageId = requestMessageId;
	}
	public byte getResult() {
		return result;
	}
	public void setResult(byte result) {
		this.result = result;
	}
	
	public boolean isSuccess()
	{
		return this.getResult() == (byte)0;
	}
	
	

}
