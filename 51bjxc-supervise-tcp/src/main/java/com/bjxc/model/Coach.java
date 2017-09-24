package com.bjxc.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Coach {
	private Integer id;
	private Integer userId;
	private Integer insId;
	private Integer sex;
	private String name;
	private String mobile;
	private String idcard;
	private String address;
	private String photo;
	private Integer subject;
	
	/**
	 * 是否全国备案 (1 已备案)
	 */
	private Integer isCountry;
	/**
	 * 是否省备案 (1 已备案)
	 */
	private Integer isProvince;
	

	public Integer getIsCountry() {
		return isCountry;
	}

	public void setIsCountry(Integer isCountry) {
		this.isCountry = isCountry;
	}

	public Integer getIsProvince() {
		return isProvince;
	}

	public void setIsProvince(Integer isProvince) {
		this.isProvince = isProvince;
	}

	/**
	 * 指纹图片
	 */
	private String fingerprint;
	/**
	 * 驾驶证号
	 */
	private String drilicence;
	/**
	 * 领证时间
	 */
	private Date receiveTime;
	/**
	 * 准驾车型  A1,A2 多项 用,隔开
	 */
	private String dripermitted;
	/**
	 * 准教车型  A1,A2 多项 用,隔开
	 */
	private String teachpermitted;
	/**
	 * 教练员编号
	 */
	private String coachnum;
	/**
	 * 联业资格等级1-一级 2-二级 3-三级 4-四级
	 */
	private Integer occupationlevel;
	/**
	 * 供职状态 0-在职 1-离职
	 */
	private Integer employstatus;
	/**
	 * 星级
	 */
	private Integer star;
	private Date birth;
	/**
	 * 入职时间
	 */
	private Date hiredate;
	/**
	 * 离职时间
	 */
	private Date leavedate;
	/**
	 * 开始驾车时间
	 */
	private Date drivingTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 教练车品牌
	 */
	private String carBrand;
	/**
	 * 教练车车牌
	 */
	private String carPlate;
	private Integer stationId;
	private Integer drivingFieldId;
	private Integer language;
	private Double price;
	private String rLicnum;
	private String occupationno;
	/**
	 * 照片文件ID
	 */
	private Integer photoId;
	/**
	 * 指纹图片ID
	 */
	private Integer fingerprintId;
	private String stationName;
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Integer getDrivingFieldId() {
		return drivingFieldId;
	}

	public void setDrivingFieldId(Integer drivingFieldId) {
		this.drivingFieldId = drivingFieldId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

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
	
	public Integer getSex() {
		return sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
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
	
	public String getIdcard() {
		return idcard;
	}
	
	public void setIdcard(String idcard) {
		this.idcard = idcard;
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
	
	public String getDrilicence() {
		return drilicence;
	}
	
	public void setDrilicence(String drilicence) {
		this.drilicence = drilicence;
	}
	
	public String getDripermitted() {
		return dripermitted;
	}
	
	public void setDripermitted(String dripermitted) {
		this.dripermitted = dripermitted;
	}
	
	public String getTeachpermitted() {
		return teachpermitted;
	}
	
	public void setTeachpermitted(String teachpermitted) {
		this.teachpermitted = teachpermitted;
	}
	
	public String getCoachnum() {
		return coachnum;
	}
	
	public void setCoachnum(String coachnum) {
		this.coachnum = coachnum;
	}
	
	public Integer getOccupationlevel() {
		return occupationlevel;
	}
	
	public void setOccupationlevel(Integer occupationlevel) {
		this.occupationlevel = occupationlevel;
	}
	
	public Integer getEmploystatus() {
		return employstatus;
	}
	
	public void setEmploystatus(Integer employstatus) {
		this.employstatus = employstatus;
	}
	
	public Integer getStar() {
		return star;
	}
	
	public void setStar(Integer star) {
		this.star = star;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getBirth() {
		return birth;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getHiredate() {
		return hiredate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getLeavedate() {
		return leavedate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getDrivingTime() {
		return drivingTime;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setDrivingTime(Date drivingTime) {
		this.drivingTime = drivingTime;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCarBrand() {
		return carBrand;
	}
	
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	
	public String getCarPlate() {
		return carPlate;
	}
	
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public Integer getSubject() {
		return subject;
	}

	public void setSubject(Integer subject) {
		this.subject = subject;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getReceiveTime() {
		return receiveTime;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getrLicnum() {
		return rLicnum;
	}
	public void setrLicnum(String rLicnum) {
		this.rLicnum = rLicnum;
	}

	public String getOccupationno() {
		return occupationno;
	}

	public void setOccupationno(String occupationno) {
		this.occupationno = occupationno;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public Integer getFingerprintId() {
		return fingerprintId;
	}

	public void setFingerprintId(Integer fingerprintId) {
		this.fingerprintId = fingerprintId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
}
