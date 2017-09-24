package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ExtendMessageBody implements IBytes{
	
	private short transportId;
	private short extendMessageProperty;
	private short seq;
	
	private String deviceNo;
	
	private int dataLength;
	
	private TransportObject data;
	private byte[] check;
	
	public ExtendMessageBody(){
		
	}
	
	public ExtendMessageBody(byte[] bytes)
	{
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);

		transportId = hbuf.readShort();
		extendMessageProperty = hbuf.readShort();
		seq = hbuf.readShort();
		
		byte[] deviceNoBytes = new byte[16];
		hbuf.readBytes(deviceNoBytes);
		try {
			deviceNo = new String(deviceNoBytes,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		dataLength = hbuf.readInt();
		System.out.println("transportId: "+transportId);
		System.out.println("DataLength: "+dataLength);
		if(dataLength!=0){
		byte[] dataBytes = new byte[dataLength];
		hbuf.readBytes(dataBytes);
		switch(transportId){
			case (short)0x882022:
				data = new LocationTemporary(dataBytes);
				break;
			case (short)0x0101:
				data = new LoginCoach(dataBytes);
			break;
			case (short)0x8101:
				data = new ResponseCoachLogin(dataBytes);
				break;
			case (short)0x0102:
				data = new LogoutCoach(dataBytes);
				break;
			case (short)0x8102:
				data = new ResponseCoachLogout(dataBytes);
				break;
			case (short)0x8103:
				data = new Terminal(dataBytes);
				break;
			case (short)0x0104:
				data = new ResponseTerminalQuery(dataBytes);
			break;
			case (short)0x8105:
				data = new TerminalOrder(dataBytes);
				break;
			case (short)0x8106:
				data = new TerminalQuery(dataBytes);
				break;
			case (short)0x0201:
				data = new LoginStudent(dataBytes);
				break;
			case (short)0x8201:
				data = new ResponseStudentLogin(dataBytes);
				break;
			case (short)0x0202:
				data = new LogoutStudent(dataBytes);
				break;
			case (short)0x8202:
				data = new ResponseStudentLogout(dataBytes);
				break;
			case (short)0x0203:
				data = new StudyingTime(dataBytes);
				break;
			case (short)0x0205:
				data = new StudyingTimeOrder(dataBytes);
				break;
			case (short)0x8205:
				data = new ResponseStudyingTimeOrder(dataBytes);
				break;
			case (short)0x0301:
				data = new PhotoImmediately(dataBytes);
				break;
			case (short)0x8301:
				data = new ResponsePhotoImmediately(dataBytes);
				break;
			case (short)0x0302:
				data = new PhotoQuery(dataBytes);
				break;
			case (short)0x8302:
				data = new ResponsePhotoQuery(dataBytes);
				break;
			case (short)0x0303:
				data = new PhotoUpQuery(dataBytes);
				break;
			case (short)0x8303:
				data = new ResponsePhotoUpQuery(dataBytes);
				break;
			case (short)0x0304:
				data = new PhotoUploadCertain(dataBytes);
				break;
			case (short)0x8304:
				data = new ResponsePhotoUploadCertain(dataBytes);
				break;
			case (short)0x0305:
				data = new PhotoUploadInit(dataBytes);
				break;
			case (short)0x8305:
				data = new ResponsePhotoUploadInit(dataBytes);
				break;
			case (short)0x0306:
				data = new PhotoFileUpload(dataBytes);
				break;
			case (short)0x0501:
				data = new TimeTerminalSetParam(dataBytes);
				break;
			case (short)0x8501:
				data = new ResponseTimeTerminalSetParam(dataBytes);
				break;
			case (short)0x0502:
				data = new UnableTraining(dataBytes);
				break;
			case (short)0x8502:
				data = new ResponseUnableTraining(dataBytes);
				break;
			case (short)0x0503:
				data = new TimeTerminalQueryParam(dataBytes);
				break;
			case (short)0x8503:
				//消息体为空
				break;
		}
		}
		if(hbuf.isReadable()){
			check = new byte[hbuf.readableBytes()];
			hbuf.readBytes(check);
		}
	}
	
	public byte[] getBytes()
	{
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeShort(transportId);
		hbuf.writeShort(extendMessageProperty);
		hbuf.writeShort(seq);
		try
		{
			hbuf.writeBytes(deviceNo.getBytes("GBK"));
			if(data!=null){
				hbuf.writeInt(data.getBytes().length);
				hbuf.writeBytes(data.getBytes());
			}else{
				hbuf.writeInt(0);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(check!=null){
			hbuf.writeBytes(check);
		}
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}
	
	
	/**
	 * 获取用于检验鉴权密文的byte
	 * @return
	 */
	public byte[] getCheckBytes()
	{
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeShort(transportId);
		hbuf.writeShort(extendMessageProperty);
		hbuf.writeShort(seq);
		try
		{
			hbuf.writeBytes(deviceNo.getBytes("GBK"));
			if(data!=null){
				hbuf.writeInt(data.getBytes().length);
				hbuf.writeBytes(data.getBytes());
			}else{
				hbuf.writeInt(0);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		byte[] result = new byte[hbuf.readableBytes()];
		hbuf.readBytes(result);
		return result;
	}


	
	public byte[] getCheck() {
		return check;
	}

	public void setCheck(byte[] check) {
		this.check = check;
	}

	public short getTransportId() {
		return transportId;
	}

	public void setTransportId(short transportId) {
		this.transportId = transportId;
	}

	public short getExtendMessageProperty() {
		return extendMessageProperty;
	}

	public void setExtendMessageProperty(short extendMessageProperty) {
		this.extendMessageProperty = extendMessageProperty;
	}

	public short getSeq() {
		return seq;
	}

	public void setSeq(short seq) {
		this.seq = seq;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public TransportObject getData() {
		return data;
	}

	public void setData(TransportObject data) {
		this.data = data;
	}

}
