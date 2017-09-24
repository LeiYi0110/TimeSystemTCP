package com.bjxc.supervise.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 透传消息ID：0x0305
 * 应答属性：0x01
 * 客户端在开始照片数据包上传前使用该消息将照片相关信息上传服务器端，照片上传初始化消息数据格式见表B.53。
 * @author dev
 *
 */
public class PhotoUploadInit extends TransportObject {
	private String photoNum;	//照片编号 BYTE[10] 计时终端自行编号，仅使用0-9
	private String stuNum;	//学员或教练员编号 BYTE [16] 统一编号
	private byte upMode;	//上传模式 BYTE 1：自动请求上传； 129：终端主动拍照上传； 255：停止拍摄并上传图片
	private byte cameraNum;	//摄像头通道号 BYTE 0：自动 1~255 表示通道号
	private byte photoSize;	//图片尺寸 BYTE 0x01：320×240； 0x02：640×480； 0x03：800×600； 0x04：1024×768； 0x05：176×144[Qcif]； 0x06：352×288[Cif]； 0x07：704×288[HALF D1]； 0x08：704×576[D1]； 注：终端若不支持系统要求的分辨率，则取最接近的分辨率拍摄并上传
	/*发起图片的事件类型，定义如下：
	0：中心查询的图片；
	1：紧急报警主动上传的图片；
	2：关车门后达到指定车速主动上传的图片；
	3：侧翻报警主动上传的图片；
	4：上客；
	5：定时拍照；
	6：进区域；
	7：出区域；
	8：事故疑点(紧急刹车)；
	9：开车门；
	17：学员登录拍照；
	18：学员登出拍照；
	19：学员培训过程中拍照；
	20：教练员登录拍照；
	21：教练员登出拍照*/
	private byte photoType;	//发起图片的事件类型 BYTE
	private short count;	//总包数 WORD
	private int countSize;	//照片数据大小 DWORD
	private int classId;	//课堂ID DWORD 标识学员的一次培训过程，计时终端自行使用
	private LocationInfo locationInfo;	//附加GNSS数据包 BYTE[328] 照片拍摄时的卫星定位数据，见表B.21、表B24，由位置基本信息+位置附加信息项中的里程和发动机转速组成
	private byte face;	//人脸识别置信度 BYTE 0-100，数值越大置信度越高

	public PhotoUploadInit(){}
	
	public PhotoUploadInit(byte[] bytes){
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);
		byte[] photoNumBytes = new byte[10];
		hbuf.readBytes(photoNumBytes);
		byte[] stuNumBytes = new byte[16];
		hbuf.readBytes(stuNumBytes);
		upMode = hbuf.readByte();
		cameraNum = hbuf.readByte();
		photoSize = hbuf.readByte();
		photoType = hbuf.readByte();
		count = hbuf.readShort();
		countSize = hbuf.readInt();
		classId = hbuf.readInt();
		byte[] loactionBytes = new byte[38];
		hbuf.readBytes(loactionBytes);
		face = hbuf.readByte();
		try {
			photoNum= new String(photoNumBytes,"GBK");
			stuNum=new String(stuNumBytes,"GBK");
			locationInfo = new LocationInfo(loactionBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try{
			buffer.writeBytes(photoNum.getBytes("GBK"));
			buffer.writeBytes(stuNum.getBytes("GBK"));
			buffer.writeByte(upMode);
			buffer.writeByte(cameraNum);
			buffer.writeByte(photoSize);
			buffer.writeByte(photoType);
			buffer.writeShort(count);
			buffer.writeInt(countSize);
			buffer.writeInt(classId);
			buffer.writeBytes(locationInfo.getBytes());
			buffer.writeByte(face);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	
	public byte getPhotoType() {
		return photoType;
	}

	public void setPhotoType(byte photoType) {
		this.photoType = photoType;
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

	public String getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(String photoNum) {
		this.photoNum = photoNum;
	}

	public short getCount() {
		return count;
	}

	public void setCount(short count) {
		this.count = count;
	}

	public String getStuNum() {
		return stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public int getCountSize() {
		return countSize;
	}

	public void setCountSize(int countSize) {
		this.countSize = countSize;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public byte getFace() {
		return face;
	}

	public void setFace(byte face) {
		this.face = face;
	}

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}

	@Override
	public String toString() {
		return "PhotoUploadInit [photoNum=" + photoNum + ", stuNum=" + stuNum + ", upMode=" + upMode + ", cameraNum="
				+ cameraNum + ", photoSize=" + photoSize + ", photoType=" + photoType + ", count=" + count
				+ ", countSize=" + countSize + ", classId=" + classId + ", locationInfo=" + locationInfo + ", face="
				+ face + "]";
	}
	
}
