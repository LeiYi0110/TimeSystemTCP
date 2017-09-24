package com.bjxc.model;

import java.util.Date;

public class TrainingLog {
	
	private Integer id;
	private String studentnum;
	private String coachnum;
	private String recordnum;
	private Integer uploadtype;
	private Integer courseid;
	private Date recordtime;
	private String course;
	private Integer status;
	private Integer maxspeed;
	private Integer mileage;
	private Integer isinstitutionpass;
	private Integer isprovincepass;
	private Integer locationid;
	private Integer trainingrecordid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRecordnum() {
		return recordnum;
	}
	public void setRecordnum(String recordnum) {
		this.recordnum = recordnum;
	}
	public Integer getUploadtype() {
		return uploadtype;
	}
	public void setUploadtype(Integer uploadtype) {
		this.uploadtype = uploadtype;
	}
	public String getStudentnum() {
		return studentnum;
	}
	public void setStudentnum(String studentnum) {
		this.studentnum = studentnum;
	}
	public String getCoachnum() {
		return coachnum;
	}
	public void setCoachnum(String coachnum) {
		this.coachnum = coachnum;
	}
	public Integer getCourseid() {
		return courseid;
	}
	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}
	public Date getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getMaxspeed() {
		return maxspeed;
	}
	public void setMaxspeed(Integer maxspeed) {
		this.maxspeed = maxspeed;
	}
	public Integer getMileage() {
		return mileage;
	}
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	public Integer getTrainingrecordid() {
		return trainingrecordid;
	}
	public void setTrainingrecordid(Integer trainingrecordid) {
		this.trainingrecordid = trainingrecordid;
	}
	public Integer getIsinstitutionpass() {
		return isinstitutionpass;
	}
	public void setIsinstitutionpass(Integer isinstitutionpass) {
		this.isinstitutionpass = isinstitutionpass;
	}
	public Integer getIsprovincepass() {
		return isprovincepass;
	}
	public void setIsprovincepass(Integer isprovincepass) {
		this.isprovincepass = isprovincepass;
	}
	public Integer getLocationid() {
		return locationid;
	}
	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}
}
