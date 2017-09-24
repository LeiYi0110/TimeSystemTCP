package com.bjxc.model;

import java.util.Date;

public class DeviceImage {
	private Integer id;
	private String deviceNum;
	private String imageNum;
	private String objectNum;
	private Date recordTime;
	private Integer uploadMode;
	private Integer channelNo;
	private byte imageSize;
	private Integer event;
	private Integer courseId;
	private Integer faceProbability;
	private String imageUrl;
	private Integer isUpload;
	private Integer packageNum;
	private Integer dataSize;
	private String photoId;
	private Integer locationId;
	private Integer trainingRecordId;
	private Integer trainingLogId;
	private Date createtime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImageNum() {
		return imageNum;
	}
	public void setImageNum(String imageNum) {
		this.imageNum = imageNum;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public String getObjectNum() {
		return objectNum;
	}
	public void setObjectNum(String objectNum) {
		this.objectNum = objectNum;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public Integer getUploadMode() {
		return uploadMode;
	}
	public void setUploadMode(Integer uploadMode) {
		this.uploadMode = uploadMode;
	}
	public Integer getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}
	public byte getImageSize() {
		return imageSize;
	}
	public void setImageSize(byte imageSize) {
		this.imageSize = imageSize;
	}
	public Integer getEvent() {
		return event;
	}
	public void setEvent(Integer event) {
		this.event = event;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getFaceProbability() {
		return faceProbability;
	}
	public void setFaceProbability(Integer faceProbability) {
		this.faceProbability = faceProbability;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
	public Integer getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(Integer packageNum) {
		this.packageNum = packageNum;
	}
	public Integer getDataSize() {
		return dataSize;
	}
	public void setDataSize(Integer dataSize) {
		this.dataSize = dataSize;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public Integer getTrainingRecordId() {
		return trainingRecordId;
	}
	public void setTrainingRecordId(Integer trainingRecordId) {
		this.trainingRecordId = trainingRecordId;
	}
	public Integer getTrainingLogId() {
		return trainingLogId;
	}
	public void setTrainingLogId(Integer trainingLogId) {
		this.trainingLogId = trainingLogId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
