package com.bjxc.supervise.model;

import com.bjxc.supervise.netty.HexUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

/**
 * 上报学时记录
 * 透传消息ID：0x0203
 * 应答属性：0x01
 * 对于上报学时记录消息，服务器端应回复通用应答，此消息可盲区补传，其数据格式见表B.42。
 * @author dev
 *
 */
public class StudyingTime extends TransportObject {
	
	private String studyingTimeNum;	//学时记录编号 BYTE[26]
	private byte uploadType;	//上报类型 BYTE 0x01：自动上报；0x02：应中心要求上报。如果是应中心要求上传，则本次上传作业的作业序号保持与请求上传消息的作业序号一致，后续分段上传的作业序号也保持一致。
	private String studentNum;	//学员编号 BYTE[16]
	private String coachNum;	//教练编号 BYTE[16]
	private int courseId;	//课堂ID	DWORD
	private String recordTime;	//记录产生时间 BCD[3] 格式： HHmmss
	private String course;	//培训课程 BCD[5] 课程编码，见A4.2
	private byte recordStatus;	//记录状态 BYTE
	private short maxSpeed;	//最大速度 WORD 1min内车辆达到的最大卫星定位速度，1/10km/h
	private short mileage;	//里程 WORD 车辆1min内行驶的总里程，1/10km
	private LocationInfo locationInfo; //附加GNSS数据包 BYTE[38] 1min内第30s的卫星定位数据，见表B.21、表B24，由位置基本信息+位置附加信息项中的里程和发动机转速组成

	public StudyingTime() {
		super();
	}
	
	public StudyingTime(byte[] bytes) {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		byte[] studyingTimeNumBytes = new byte[26];
		copiedBuffer.readBytes(studyingTimeNumBytes);
		uploadType = copiedBuffer.readByte();
		byte[] studentNumBytes = new byte[16];
		copiedBuffer.readBytes(studentNumBytes);
		byte[] coachNumBytes = new byte[16];
		copiedBuffer.readBytes(coachNumBytes);
		courseId = copiedBuffer.readInt();
		byte[] recordTimeBytes = new byte[3];
		copiedBuffer.readBytes(recordTimeBytes);
		recordTime = HexUtils.bcd2Str(recordTimeBytes);
		byte[] courseBytes = new byte[5];
		copiedBuffer.readBytes(courseBytes);
		course = HexUtils.bcd2Str(courseBytes);
		recordStatus = copiedBuffer.readByte();
		maxSpeed = copiedBuffer.readShort();
		mileage = copiedBuffer.readShort();
		byte[] locationInfoBytes = new byte[bytes.length-76];
		copiedBuffer.readBytes(locationInfoBytes);
		try {
			studyingTimeNum = new String(studyingTimeNumBytes,"GBK");
			studentNum = new String(studentNumBytes,"GBK");
			coachNum = new String(coachNumBytes,"GBK");
			locationInfo = new LocationInfo(locationInfoBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(studyingTimeNum.getBytes("GBK"));
			buffer.writeByte(uploadType);
			buffer.writeBytes(studentNum.getBytes("GBK"));
			buffer.writeBytes(coachNum.getBytes("GBK"));
			buffer.writeInt(courseId);
			buffer.writeBytes(HexUtils.str2Bcd(recordTime));
			buffer.writeBytes(HexUtils.str2Bcd(course));
			buffer.writeByte(recordStatus);
			buffer.writeShort(maxSpeed);
			buffer.writeShort(mileage);
			buffer.writeBytes(locationInfo.getBytes());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}

	public String getStudyingTimeNum() {
		return studyingTimeNum;
	}

	public void setStudyingTimeNum(String studyingTimeNum) {
		this.studyingTimeNum = studyingTimeNum;
	}

	public byte getUploadType() {
		return uploadType;
	}

	public void setUploadType(byte uploadType) {
		this.uploadType = uploadType;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getCoachNum() {
		return coachNum;
	}

	public void setCoachNum(String coachNum) {
		this.coachNum = coachNum;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public byte getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(byte recordStatus) {
		this.recordStatus = recordStatus;
	}

	public short getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(short maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public short getMileage() {
		return mileage;
	}

	public void setMileage(short mileage) {
		this.mileage = mileage;
	}

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}

	@Override
	public String toString() {
		return "StudyingTime [studyingTimeNum=" + studyingTimeNum + ", uploadType=" + uploadType + ", studentNum="
				+ studentNum + ", coachNum=" + coachNum + ", courseId=" + courseId + ", recordTime="
				+ recordTime + ", course=" + course + ", recordStatus=" + recordStatus
				+ ", maxSpeed=" + maxSpeed + ", mileage=" + mileage + ", locationInfo=" + locationInfo + "]";
	}

}
