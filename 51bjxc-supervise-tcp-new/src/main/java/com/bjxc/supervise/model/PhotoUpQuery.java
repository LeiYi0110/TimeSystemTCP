package com.bjxc.supervise.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 透传消息ID：0x0303
 * 应答属性：0x01
 * 客户端上报服务器端查询照片的结果，上报照片查询结果消息数据格式见表B.49。
 * @author dev
 *
 */
public class PhotoUpQuery extends TransportObject {
	
	private byte isUploadEnd;	//是否上报结束 BYTE 0：否，1：是 如果查询结果尚未发完，则为0x00
	private byte count;	//符合条件的照片总数 BYTE 总数n，为0则无后续字段
	private byte reUploadCount;	//此次发送的照片数目 BYTE 数目m
	private List<String> paramList;	//照片编号1 BYTE[10],照片编号2 BYTE[10]...

	public PhotoUpQuery() {
		super();
	}
	
	public PhotoUpQuery(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		isUploadEnd = copiedBuffer.readByte();
		count = copiedBuffer.readByte();
		reUploadCount = copiedBuffer.readByte();
		paramList = new ArrayList<String>();
		while(copiedBuffer.readableBytes()>0){
			byte[] stringBytes = new byte[10];
			copiedBuffer.readBytes(stringBytes);
			try {
				String string  = new String(stringBytes,"GBK");
				paramList.add(string);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(isUploadEnd);
		buffer.writeByte(count);
		buffer.writeByte(reUploadCount);
		try {
			if(paramList!=null){
				for (String string : paramList) {
					buffer.writeBytes(string.getBytes("GBK"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public byte getIsUploadEnd() {
		return isUploadEnd;
	}

	public void setIsUploadEnd(byte isUploadEnd) {
		this.isUploadEnd = isUploadEnd;
	}

	public byte getCount() {
		return count;
	}

	public void setCount(byte count) {
		this.count = count;
	}

	public byte getReUploadCount() {
		return reUploadCount;
	}

	public void setReUploadCount(byte reUploadCount) {
		this.reUploadCount = reUploadCount;
	}

	public List<String> getParamList() {
		return paramList;
	}

	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}

	@Override
	public String toString() {
		return "PhotoUpQuery [isUploadEnd=" + isUploadEnd + ", count=" + count + ", reUploadCount=" + reUploadCount
				+ ", paramList=" + paramList + "]";
	}

}
