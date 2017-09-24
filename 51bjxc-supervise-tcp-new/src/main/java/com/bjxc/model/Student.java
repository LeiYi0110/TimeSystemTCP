package com.bjxc.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Student {
	private Integer id;			//  primary key,
	private Integer userId;		//	'用户ID',
	private Integer insId;		//	'培训机构ID',
	private String name;			//	'姓名',
	private String mobile;		//  '电话号码',
	private Integer cradtype;		//	'证件类型1-身份证2-护照3-军官证4-其它',
	private String cardnum;		//	'证件号',
	private String nationality;	//	'国籍',
	private Integer sex;			//	'性别1-男 2-女',
	private String address;		//	'联系地址',
	private String photo;	
	//	'头像照片地址',
	private String fingerprint;	//  '指纹图片地址',
	private Integer busitype;		//	'业务类型0-初领1-增领2-其它',
	private String  drilicnum;	//	'驾驶证号',
	private Date fstdrilicdate;	//	'初次领证日期',
	private String perdritype;	//	'原准驾车型',
	private String traintype;		//	'培训车型',
	private Date applydate;		//	'报名时间',
	private Integer status;		//	'-1-失效 1-报名 2-学习中 3- 拿到驾照',
	private String stunum;		//	'学员统一编号',
	private Date createTime;		//	'创建时间'
	private String refereeMobile;
	private String refereeName;
	private String classTypeName;   //班型名字
	private Integer subjectId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getInsId() {
		return insId;
	}
	public void setInsId(Integer insId) {
		this.insId = insId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getCradtype() {
		return cradtype;
	}
	public void setCradtype(Integer cradtype) {
		this.cradtype = cradtype;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	public Integer getBusitype() {
		return busitype;
	}
	public void setBusitype(Integer busitype) {
		this.busitype = busitype;
	}
	public String getDrilicnum() {
		return drilicnum;
	}
	public void setDrilicnum(String drilicnum) {
		this.drilicnum = drilicnum;
	}
	public Date getFstdrilicdate() {
		return fstdrilicdate;
	}
	public void setFstdrilicdate(Date fstdrilicdate) {
		this.fstdrilicdate = fstdrilicdate;
	}
	public String getPerdritype() {
		return perdritype;
	}
	public void setPerdritype(String perdritype) {
		this.perdritype = perdritype;
	}
	public String getTraintype() {
		return traintype;
	}
	public void setTraintype(String traintype) {
		this.traintype = traintype;
	}
	public Date getApplydate() {
		return applydate;
	}
	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStunum() {
		return stunum;
	}
	public void setStunum(String stunum) {
		this.stunum = stunum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRefereeMobile() {
		return refereeMobile;
	}
	public void setRefereeMobile(String refereeMobile) {
		this.refereeMobile = refereeMobile;
	}
	public String getRefereeName() {
		return refereeName;
	}
	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}
	public String getClassTypeName() {
		return classTypeName;
	}
	public void setClassTypeName(String classTypeName) {
		this.classTypeName = classTypeName;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
}
