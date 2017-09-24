package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 透传消息ID：0x0301
 * 客户端使用该消息回复服务器端的立即拍照指令，应答后执行拍照并通过透传消息ID0x0305和0x0306开始上传照片。立即拍照应答消息数据格式见表B.46。
 * @author dev
 *
 */
public class PhotoImmediately extends TransportObject {
	
	/**
	 * 1：可以拍摄；
	 * 2：拍照失败；
	 * 3：SD卡故障；
	 * 4：正在拍照，不能执行；
	 * 5：重新连接摄像头，不能保证拍照；
	 * 6：正在上传查询照片，不能执行。
	 */
	private byte resultCode;	//执行结果 BYTE
	private byte upMode;	//上传模式 BYTE 与下行命令一致
	private byte cameraNum;	//摄像头通道号 BYTE 实际拍摄的通道号
	/**
	 * 0x01：320×240；
	 * 0x02：640×480；
	 * 0x03：800×600；
	 * 0x04：1024×768；
	 * 0x05：176×144[Qcif]；
	 * 0x06：352×288[Cif]；
	 * 0x07：704×288[HALF D1]；
	 * 0x08：704×576[D1]；
	 * 注：终端若不支持系统要求的分辨率，则取最接近的分辨率拍摄并上传
	 */
	private byte photoSize;	//图片实际尺寸 BYTE

	public PhotoImmediately() {
		super();
	}
	
	public PhotoImmediately(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		resultCode = copiedBuffer.readByte();
		upMode = copiedBuffer.readByte();
		cameraNum = copiedBuffer.readByte();
		photoSize = copiedBuffer.readByte();
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(resultCode);
		buffer.writeByte(upMode);
		buffer.writeByte(cameraNum);
		buffer.writeByte(photoSize);
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public byte getResultCode() {
		return resultCode;
	}

	public void setResultCode(byte resultCode) {
		this.resultCode = resultCode;
	}

	public byte getUpMode() {
		return upMode;
	}

	public void setUpMode(byte upMode) {
		this.upMode = upMode;
	}

	public byte getCameraNum() {
		return cameraNum;
	}

	public void setCameraNum(byte cameraNum) {
		this.cameraNum = cameraNum;
	}

	public byte getPhotoSize() {
		return photoSize;
	}

	public void setPhotoSize(byte photoSize) {
		this.photoSize = photoSize;
	}

	@Override
	public String toString() {
		return "PhotoImmediately [resultCode=" + resultCode + ", upMode=" + upMode + ", cameraNum=" + cameraNum
				+ ", photoSize=" + photoSize + "]";
	}

}
