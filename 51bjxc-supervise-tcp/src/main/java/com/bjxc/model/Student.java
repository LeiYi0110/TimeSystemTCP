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
	private String idcard;		//	'证件号',
	private String nationality;	//	'国籍',
	private Integer sex;			//	'性别1-男 2-女',
	private String address;		//	'联系地址',
	private String photo;			//	'头像照片地址',
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
	private String refereeMobiles;
	private String refereeName;
	private String coachName;    //教练名字
	private Integer coachId;
	private String classTypeName;   //班型名字
	private Integer stationId;
	private Integer classTypeId;
	//应交学费
	private Integer tuition;
	//已交学费
	private Integer alreadyPay;
	//欠费
	private Integer arrearage;
	
	//服务网点名称
	private String stationName;
	
	private Integer subjectId;
	
	private Integer payType; //班型支付类型 
	
	private Integer isWrong;//导入的数据是否有误
	
	private Integer reservation;//预约数
	
	private Integer classhour;//班型课时数
	
	private String recordnum;	//档案编号
	
	private String subjectName;//科目名称
	
	private Integer refundStatus;//退费状态
	private String documenturl;  //结业证书文件url
	private String signatureurl;   //电子签章url
	private String organno;      //发证机关编号
	private String certificateno;    //结业证书编号
	
	private String remark;    //备注
	
	private Integer paymentType;   //支付方式 0-现金 1-微信-现场 2-支付宝-现场 3-其他
	private Date day;	//预警开始日期
	private Date mondayOne;	//获取本周周一时间
	private Date mondayDay;//获取本周周日时间 
	private Date subjectTime;//科目一时间
	private Integer overdueTime;//学员逾期剩余时间
	private Integer fileNum;
	private Integer isProvince;
	private Integer isCountry;
	private Integer isTCPLogin;
	private Date TCPStatusChangeTime;
	private Date TCPLoginTime;
	private Date TCPLogoutTime;
	private Date lastestUploadTime;
	
	public String getDocumenturl() {
		return documenturl;
	}
	public void setDocumenturl(String documenturl) {
		this.documenturl = documenturl;
	}
	public String getSignatureurl() {
		return signatureurl;
	}
	public void setSignatureurl(String signatureurl) {
		this.signatureurl = signatureurl;
	}
	public String getOrganno() {
		return organno;
	}
	public void setOrganno(String organno) {
		this.organno = organno;
	}
	public String getCertificateno() {
		return certificateno;
	}
	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}
	public String getRefereeMobiles() {
		return refereeMobiles;
	}
	public void setRefereeMobiles(String refereeMobiles) {
		this.refereeMobiles = refereeMobiles;
	}
	public String getRefereeName() {
		return refereeName;
	}
	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getRefereeMobile() {
		return refereeMobile;
	}
	public void setRefereeMobile(String refereeMobile) {
		this.refereeMobile = refereeMobile;
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
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
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
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getFstdrilicdate() {
		return fstdrilicdate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
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
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getApplydate() {
		return applydate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String getClassTypeName() {
		return classTypeName;
	}
	public void setClassTypeName(String classTypeName) {
		this.classTypeName = classTypeName;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public Integer getClassTypeId() {
		return classTypeId;
	}
	public void setClassTypeId(Integer classTypeId) {
		this.classTypeId = classTypeId;
	}
	
	
	public Integer getTuition() {
		if(tuition != null || new Integer(0).equals(tuition)){
			return tuition/100;
		}
		return tuition;
	}
	public void setTuition(Integer tuition) {
		this.tuition = tuition;
	}
	public Integer getAlreadyPay() {
		if(alreadyPay != null || new Integer(0).equals(alreadyPay)){
			alreadyPay=alreadyPay/100;
		}
		return alreadyPay;
	}
	public void setAlreadyPay(Integer alreadyPay) {
		this.alreadyPay = alreadyPay;
	}
	public Integer getArrearage() {
		if(arrearage != null || new Integer(0).equals(arrearage)){
			return arrearage/100;
		}
		return arrearage;
	}
	public void setArrearage(Integer arrearage) {
		this.arrearage = arrearage;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getCoachId() {
		return coachId;
	}
	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}
	public Integer getIsWrong() {
		return isWrong;
	}
	public void setIsWrong(Integer isWrong) {
		this.isWrong = isWrong;
	}
	public Integer getReservation() {
		return reservation;
	}
	public void setReservation(Integer reservation) {
		this.reservation = reservation;
	}
	public Integer getClasshour() {
		return classhour;
	}
	public void setClasshour(Integer classhour) {
		this.classhour = classhour;
	}
	public String getRecordnum() {
		return recordnum;
	}
	public void setRecordnum(String recordnum) {
		this.recordnum = recordnum;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getDay() {
		return day;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setDay(Date day) {
		this.day = day;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getMondayOne() {
		 Calendar c = Calendar.getInstance();
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0){
		   day_of_week = 7;
		  }
		  c.add(Calendar.DATE, -day_of_week + 1);
		  mondayOne=c.getTime();
		return mondayOne;
	}
	public void setMondayOne(Date mondayOne) {
		this.mondayOne = mondayOne;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getMondayDay() {
		Calendar c = Calendar.getInstance();
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0){
		   day_of_week = 7;
		  }
		  c.add(Calendar.DATE, -day_of_week + 7);
		  
		  mondayDay=c.getTime();
		return mondayDay;
	}
	public void setMondayDay(Date mondayDay) {
		this.mondayDay = mondayDay;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getSubjectTime() {
		return subjectTime;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setSubjectTime(Date subjectTime) {
		this.subjectTime = subjectTime;
	}
	public Integer getOverdueTime() {
		return overdueTime;
	}
	public void setOverdueTime(Integer overdueTime) {
		this.overdueTime = overdueTime;
	}
	public Integer getFileNum() {
		return fileNum;
	}
	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}
	public Integer getIsProvince() {
		return isProvince;
	}
	public void setIsProvince(Integer isProvince) {
		this.isProvince = isProvince;
	}
	public Integer getIsCountry() {
		return isCountry;
	}
	public void setIsCountry(Integer isCountry) {
		this.isCountry = isCountry;
	}
	public Integer getIsTCPLogin() {
		return isTCPLogin;
	}
	public void setIsTCPLogin(Integer isTCPLogin) {
		this.isTCPLogin = isTCPLogin;
	}
	public Date getTCPStatusChangeTime() {
		return TCPStatusChangeTime;
	}
	public void setTCPStatusChangeTime(Date tCPStatusChangeTime) {
		TCPStatusChangeTime = tCPStatusChangeTime;
	}
	public Date getTCPLoginTime() {
		return TCPLoginTime;
	}
	public void setTCPLoginTime(Date tCPLoginTime) {
		TCPLoginTime = tCPLoginTime;
	}
	public Date getTCPLogoutTime() {
		return TCPLogoutTime;
	}
	public void setTCPLogoutTime(Date tCPLogoutTime) {
		TCPLogoutTime = tCPLogoutTime;
	}
	public Date getLastestUploadTime() {
		return lastestUploadTime;
	}
	public void setLastestUploadTime(Date lastestUploadTime) {
		this.lastestUploadTime = lastestUploadTime;
	}
	
}
