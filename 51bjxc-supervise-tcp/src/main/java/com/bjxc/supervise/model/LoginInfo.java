package com.bjxc.supervise.model;

import com.bjxc.model.Ins;
import com.bjxc.model.UploadPhoto;

public class LoginInfo {
	private Coach coach;
	private Student student;
	private Device device;
	private TrainingCar trainingCar;
	private com.bjxc.model.TrainingCar jttTrainingCar; 
	private Ins ins;
	private Integer trainingRecordId;	//本次培训的电子教学日志id，在学员登录时生成
	private Integer mapMode;	//该终端上传的位置坐标模式	0.GCJ02(默认,谷歌、高德、腾讯、阿里云地图)    1.WGS84(国际标准GPS)    2.BD09(百度地图)
	
	private String cadata;
	public String getCadata() {
		return cadata;
	}
	public void setCadata(String cadata) {
		this.cadata = cadata;
	}
	public String getCapwd() {
		return capwd;
	}
	public void setCapwd(String capwd) {
		this.capwd = capwd;
	}
	private String capwd;
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public TrainingCar getTrainingCar() {
		return trainingCar;
	}
	public void setTrainingCar(TrainingCar trainingCar) {
		this.trainingCar = trainingCar;
	}
/*	public UploadPhoto getUploadPhoto() {
		return uploadPhoto;
	}
	public void setUploadPhoto(UploadPhoto uploadPhoto) {
		this.uploadPhoto = uploadPhoto;
	}*/
	public Coach getCoach() {
		return coach;
	}
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getTrainingRecordId() {
		return trainingRecordId;
	}
	public void setTrainingRecordId(Integer trainingRecordId) {
		this.trainingRecordId = trainingRecordId;
	}
	public Integer getMapMode() {
		return mapMode;
	}
	public void setMapMode(Integer mapMode) {
		this.mapMode = mapMode;
	}
	public com.bjxc.model.TrainingCar getJttTrainingCar() {
		return jttTrainingCar;
	}
	public void setJttTrainingCar(com.bjxc.model.TrainingCar jttTrainingCar) {
		this.jttTrainingCar = jttTrainingCar;
	}
	public Ins getIns() {
		return ins;
	}
	public void setIns(Ins ins) {
		this.ins = ins;
	}
}
