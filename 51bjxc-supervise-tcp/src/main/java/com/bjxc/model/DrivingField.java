package com.bjxc.model;


public class DrivingField {

	/**
	 * 编号
	 */
	private Integer id;

	/**
	 * 场地编号
	 */
	private String seq;
	private Integer seqint;

	/**
	 * 练车场地(培训机构id)
	 */

	private Integer insId;
	/**
	 * 练车场地地址
	 */
	private String address;
	/**
	 * 经纬度
	 */
	private String location;
	/**
	 * 练车场地名称
	 */
	private String name;
	/**
	 * 区
	 */
	private String area;

	/**
	 * 面积
	 */
	private Integer areasize;
	/**
	 * 教练数量
	 */
	private Integer coaTotal;
	/**
	 * 已投放数量
	 */
	private Integer carTotal;

	/**
	 * 状态
	 */
	private Integer status;

	private Integer totalvehnum;

	private Integer curvehnum;

	private String ttname;
	private String vehicletype;
	private Integer areaId;
	private Integer type;

	private String province;
	private String citycs;
	private String areaname;
	private String inscode;
	private Integer isProvince;
	private Integer isNotice;
	private String reason;

	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getIsNotice() {
		return isNotice;
	}
	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getCoaTotal() {
		return coaTotal;
	}
	public void setCoaTotal(Integer coaTotal) {
		this.coaTotal = coaTotal;
	}
	public Integer getCarTotal() {
		return carTotal;
	}
	public void setCarTotal(Integer carTotal) {
		this.carTotal = carTotal;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInsId() {
		return insId;
	}
	public void setInsId(Integer insId) {
		this.insId = insId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getTotalvehnum() {
		return totalvehnum;
	}
	public void setTotalvehnum(Integer totalvehnum) {
		this.totalvehnum = totalvehnum;
	}
	public Integer getCurvehnum() {
		return curvehnum;
	}
	public void setCurvehnum(Integer curvehnum) {
		this.curvehnum = curvehnum;
	}
	public String getTtname() {
		return ttname;
	}
	public void setTtname(String ttname) {
		this.ttname = ttname;
	}
	public String getVehicletype() {
		return vehicletype;
	}
	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}
	public Integer getAreasize() {
		return areasize;
	}
	public void setAreasize(Integer areasize) {
		this.areasize = areasize;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCitycs() {
		return citycs;
	}
	public void setCitycs(String citycs) {
		this.citycs = citycs;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSeq() {
		if(seq!=null){
			Integer sed = Integer.parseInt(seq);
			String str = String.format("%04d", sed);
			return str;
		}else{
			return null;
		}

		//return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public Integer getSeqint() {
		return seqint;
	}
	public void setSeqint(Integer seqint) {
		this.seqint = seqint;
	}
	public Integer getIsProvince() {
		return isProvince;
	}
	public void setIsProvince(Integer isProvince) {
		this.isProvince = isProvince;
	}
	public String getInscode() {
		return inscode;
	}
	public void setInscode(String inscode) {
		this.inscode = inscode;
	}




}
