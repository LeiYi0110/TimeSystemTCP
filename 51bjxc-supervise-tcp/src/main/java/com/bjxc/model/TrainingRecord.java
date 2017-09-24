package com.bjxc.model;

import java.util.Date;

public class TrainingRecord {
	private Integer id;
	private Integer studentId;
	private Integer coachId;
	private Date createtime;
	private String courseCode;
	private Integer trainingcarId;
	private Integer deviceId;
	private Date startTime;
	private Date endTime;
	private String recordCode;
	private Integer courseType;
	private Integer subjectId;
	private String etrainingLogCode;
	private Integer isProvince;
	private String loginPhotoId;
	private String logoutPhotoId;
	private int pass;
	private String reason;
	private int mileage;
	private int avevelocity;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getCoachId() {
		return coachId;
	}
	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public Integer getTrainingcarId() {
		return trainingcarId;
	}
	public void setTrainingcarId(Integer trainingcarId) {
		this.trainingcarId = trainingcarId;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRecordCode() {
		return recordCode;
	}
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	public Integer getCourseType() {
		return courseType;
	}
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getEtrainingLogCode() {
		return etrainingLogCode;
	}
	public void setEtrainingLogCode(String etrainingLogCode) {
		this.etrainingLogCode = etrainingLogCode;
	}
	public Integer getIsProvince() {
		return isProvince;
	}
	public void setIsProvince(Integer isProvince) {
		this.isProvince = isProvince;
	}
	public String getLoginPhotoId() {
		return loginPhotoId;
	}
	public void setLoginPhotoId(String loginPhotoId) {
		this.loginPhotoId = loginPhotoId;
	}
	public String getLogoutPhotoId() {
		return logoutPhotoId;
	}
	public void setLogoutPhotoId(String logoutPhotoId) {
		this.logoutPhotoId = logoutPhotoId;
	}
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getMileage() {
		return mileage;
	}
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	public int getAvevelocity() {
		return avevelocity;
	}
	public void setAvevelocity(int avevelocity) {
		this.avevelocity = avevelocity;
	}
	
}
