package com.bjxc.jtt;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class JttMessage implements Comparable{
	
	//头标识
	private byte first = 0x5b;
	
	//数据长度(包含头标识, 数据头, 数据体和尾标识), 无符号4字节
	private int length; 
	//报文序列号, 无符号4字节
	private int serialNumber = JttUtils.getCurrentMessageSerialNumber(); 
	//业务类型,2字节
	private short type;
	//下级平台接入码,4字节
	private int code = 1300012;
	//协议版本号标识,3字节
	private byte[] version = {1,2,3};
	//加密标识位  0.不加密  1.加密,1字节
	private byte encrypt = 0;

	//加密秘钥,4字节
	private int key = 223344;
	
	//数据体
	private byte[] data;
	
	//从数据头到校验码前的crc16-ccitt的校验值,2字节
	private short crcCode;
	
	//尾标识,1字节
	private byte last = 0x5d;
	
	public JttMessage() {
		super();
	}

	public JttMessage(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		first = copiedBuffer.readByte();
		length = copiedBuffer.readInt();
		serialNumber = copiedBuffer.readInt();
		type = copiedBuffer.readShort();
		code = copiedBuffer.readInt();
		copiedBuffer.readBytes(version);
		encrypt = copiedBuffer.readByte();
		key = copiedBuffer.readInt();
		data = new byte[length-26];
		copiedBuffer.readBytes(data);
		crcCode = copiedBuffer.readShort();
		last = copiedBuffer.readByte();
	}
	public byte getFirst() {
		return first;
	}

	public void setFirst(byte first) {
		this.first = first;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public byte[] getVersion() {
		return version;
	}

	public void setVersion(byte[] version) {
		this.version = version;
	}

	public byte getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(byte encrypt) {
		this.encrypt = encrypt;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public short getCrcCode() {
		return crcCode;
	}

	public void setCrcCode(short crcCode) {
		this.crcCode = crcCode;
	}

	public byte getLast() {
		return last;
	}

	public void setLast(byte last) {
		this.last = last;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String toString() {
		return "JttMessage [first=" + first + ", length=" + length + ", serialNumber=" + serialNumber + ", type=" + type
				+ ", code=" + code + ", version=" + Arrays.toString(version) + ", encrypt=" + encrypt + ", key=" + key
				+ ", data=" + Arrays.toString(data) + ", crcCode=" + crcCode + ", last=" + last + "]";
	}
}
